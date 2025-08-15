package ktk.wishdroid.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ktk.wishdroid.expensetracker.presentation.ui.screens.AddExpenseScreen
import ktk.wishdroid.expensetracker.presentation.ui.screens.ExpenseListScreen
import ktk.wishdroid.expensetracker.presentation.ui.screens.ReportScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.AddExpense.route) {
        composable(Screen.AddExpense.route) { AddExpenseScreen { } }
        composable(Screen.ExpenseList.route) {
            ExpenseListScreen() { }
        }
        composable(Screen.Report.route) { ReportScreen { } }
    }
}
