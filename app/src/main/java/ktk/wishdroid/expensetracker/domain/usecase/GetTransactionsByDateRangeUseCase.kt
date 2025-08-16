package ktk.wishdroid.expensetracker.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository
import java.util.Date

class GetTransactionsByDateRangeUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(start: Long, end: Long): Flow<List<Transaction>> {
        val startDate = Date(start)
        val endDate = Date(end)

        return repository.getAllTransactions().map { list ->
            list.filter {
                val date = Date(it.timestamp)
                !date.before(startDate) && !date.after(endDate)
            }
        }
    }

}
