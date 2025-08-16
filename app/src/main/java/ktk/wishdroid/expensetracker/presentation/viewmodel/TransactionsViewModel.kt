package ktk.wishdroid.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.usecase.TransactionsUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ktk.wishdroid.expensetracker.domain.model.Category
import ktk.wishdroid.expensetracker.presentation.ui.state.AddTransactionUiState
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val useCases: TransactionsUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<UiEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun onTitleChanged(newTitle: String) {
        _uiState.update { it.copy(error = "") }
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onAmountChanged(newAmount: String) {
        _uiState.update { it.copy(error = "") }
        _uiState.update { it.copy(amount = newAmount) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { it.copy(category = category, expanded = false) }
    }

    fun onNotesAdded(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun toggleExpanded() {
        _uiState.update { it.copy(expanded = !it.expanded) }
    }

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()


    private val _selectedTransaction = MutableStateFlow<Transaction?>(null)
    val selectedTransaction: StateFlow<Transaction?> = _selectedTransaction.asStateFlow()

    init {
        getTodayAllTransaction()
    }

    fun getTodayAllTransaction() {
        viewModelScope.launch {
            useCases.getTodayTransactionsUseCase().collect { list ->
                _transactions.value = list
            }
        }
    }

    fun getTransactionsByDateRange(start: Long, end: Long) {
        viewModelScope.launch {

            useCases.getTransactionsByDateRangeUseCase(start, end).collect { list ->
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
            val validationResult = useCases.validateTransaction(transaction)
            if (!validationResult.successful) {
                _eventChannel.send(UiEvent.ShowToast(validationResult.errorMessage))
                return@launch
            }
            useCases.insertTransaction(transaction)
            _uiState.value = AddTransactionUiState()
            _eventChannel.send(UiEvent.ShowToast("Transaction added successfully"))
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            useCases.deleteTransaction(transaction)
        }
    }

    sealed class UiEvent {
        class ShowToast(val message: String?) : UiEvent()
    }
}