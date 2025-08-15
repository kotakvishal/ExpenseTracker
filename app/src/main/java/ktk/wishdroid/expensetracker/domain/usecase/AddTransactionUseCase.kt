package ktk.wishdroid.expensetracker.domain.usecase

import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository

class AddTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.insertTransaction(transaction)
    }
}
