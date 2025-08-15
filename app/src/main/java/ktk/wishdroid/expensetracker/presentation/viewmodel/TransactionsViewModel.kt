package ktk.wishdroid.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.usecase.TransactionsUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val useCases: TransactionsUseCases
) : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _selectedTransaction = MutableStateFlow<Transaction?>(null)
    val selectedTransaction: StateFlow<Transaction?> = _selectedTransaction.asStateFlow()

    init {
        getAllTransactions()
    }

    fun getAllTransactions() {
        viewModelScope.launch {
            useCases.getAllTransactions().collect { list ->
                _transactions.value = list
            }
        }
    }

    fun getTransactionById(id: Long) {
        viewModelScope.launch {
            useCases.getTransactionById(id).collect { transaction ->
                _selectedTransaction.value = transaction
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCases.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCases.deleteTransaction(transaction)
        }
    }
}