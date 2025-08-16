package ktk.wishdroid.expensetracker.domain.usecase


data class TransactionsUseCases(
    val getTransactionById: GetTransactionByIdUseCase,
    val insertTransaction: AddTransactionUseCase,
    val deleteTransaction: DeleteTransactionUseCase,
    val getAllTransactions: GetAllTransactionsUseCase,
    val validateTransaction: ValidateTransactionUseCase
)
