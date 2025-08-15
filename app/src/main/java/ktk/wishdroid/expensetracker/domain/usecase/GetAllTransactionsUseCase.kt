package ktk.wishdroid.expensetracker.domain.usecase

import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository


class GetAllTransactionsUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke() = repository.getAllTransactions()
}
