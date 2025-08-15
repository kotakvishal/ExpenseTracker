package ktk.wishdroid.expensetracker.domain.repo

import kotlinx.coroutines.flow.Flow
import ktk.wishdroid.expensetracker.domain.model.Transaction

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionById(id: Long): Flow<Transaction?>
}
