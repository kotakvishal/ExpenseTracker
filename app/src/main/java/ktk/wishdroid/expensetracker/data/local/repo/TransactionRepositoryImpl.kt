package ktk.wishdroid.expensetracker.data.local.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ktk.wishdroid.expensetracker.data.local.dao.TransactionsDao
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository
import javax.inject.Inject
import ktk.wishdroid.expensetracker.data.mappers.toDomain
import ktk.wishdroid.expensetracker.data.mappers.toEntity

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao
) : TransactionRepository {

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction.toEntity())
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTransactionById(id: Long): Flow<Transaction?> {
        return dao.getTransactionById(id).map { it?.toDomain() }
    }

}
