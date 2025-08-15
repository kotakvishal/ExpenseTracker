package ktk.wishdroid.expensetracker.presentation.navigation

sealed class Screen(val route: String, val title: String) {
    object AddExpense : Screen("add_expense", "Add")
    object ExpenseList : Screen("expense_list", "List")
    object Report : Screen("report", "Report")
}
