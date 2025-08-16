package ktk.wishdroid.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ktk.wishdroid.expensetracker.presentation.ui.screens.AddExpenseScreen
import ktk.wishdroid.expensetracker.presentation.ui.screens.ExpenseListScreen
import ktk.wishdroid.expensetracker.presentation.ui.screens.ReportScreen
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.ExpenseList.route) {
        composable(Screen.AddExpense.route) {
            AddExpenseScreen(onExpenseAdded = { navController.popBackStack() }
            )
        }
        composable(Screen.ExpenseList.route) {
            ExpenseListScreen(){
                navController.navigate(Screen.AddExpense.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(Screen.Report.route) { ReportScreen() }
    }
}
