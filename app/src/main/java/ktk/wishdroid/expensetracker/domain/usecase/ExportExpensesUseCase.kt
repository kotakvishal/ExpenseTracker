package ktk.wishdroid.expensetracker.domain.usecase

import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository

class ExportExpensesUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.exportTransactionsToCsv()
    }
}