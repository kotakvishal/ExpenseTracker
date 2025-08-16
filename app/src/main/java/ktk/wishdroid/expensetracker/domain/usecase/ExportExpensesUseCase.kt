package ktk.wishdroid.expensetracker.domain.usecase

import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository

class ExportExpensesUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Boolean {
        val valueToREturn = repository.exportTransactionsToCsv()
        println("philovishal expenses valueToREturn : $valueToREturn")
        return valueToREturn
    }
}