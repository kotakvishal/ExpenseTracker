package ktk.wishdroid.expensetracker.domain.usecase


data class TransactionsUseCases(
    val getTransactionById: GetTransactionByIdUseCase,
    val insertTransaction: AddTransactionUseCase,
    val deleteTransaction: DeleteTransactionUseCase,
    val getTodayTransactionsUseCase: GetTodayTransactionsUseCase,
    val getTransactionsByDateRangeUseCase: GetTransactionsByDateRangeUseCase,
    val validateTransaction: ValidateTransactionUseCase,
    val exportExpensesUseCase: ExportExpensesUseCase
)
