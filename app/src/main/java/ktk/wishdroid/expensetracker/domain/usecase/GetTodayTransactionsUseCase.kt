package ktk.wishdroid.expensetracker.domain.usecase

import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetTodayTransactionsUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val today = sdf.format(Date())

        return repository.getAllTransactions().map { list ->
            list.filter {
                sdf.format(Date(it.timestamp)) == today
            }
        }
    }
}
