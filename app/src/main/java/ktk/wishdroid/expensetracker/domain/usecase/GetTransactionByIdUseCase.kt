package ktk.wishdroid.expensetracker.domain.usecase

import kotlinx.coroutines.flow.Flow
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(id: Long): Flow<Transaction?> {
        return repository.getTransactionById(id)
    }
}
