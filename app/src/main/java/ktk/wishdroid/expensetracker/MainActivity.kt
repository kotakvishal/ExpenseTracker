package ktk.wishdroid.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ktk.wishdroid.expensetracker.presentation.navigation.AppNavGraph
import ktk.wishdroid.expensetracker.presentation.navigation.Screen
import ktk.wishdroid.expensetracker.presentation.ui.components.BottomNavBar
import ktk.wishdroid.expensetracker.presentation.ui.components.BottomNavItem
import ktk.wishdroid.expensetracker.ui.theme.ExpenseTrackerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val barChartIcon = ImageVector.vectorResource(id = R.drawable.ic_bar_chart)

                val bottomNavItems = listOf(
                    BottomNavItem(Screen.ExpenseList.route, "List", Icons.Default.List),
                    BottomNavItem(Screen.Report.route, "Report",barChartIcon)
                )

                Scaffold(
                    bottomBar = {
                        if (currentRoute != Screen.AddExpense.route) {
                            BottomNavBar(
                                navController,
                                items = bottomNavItems,
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavGraph(navController)
                    }
                }
            }
        }
    }
}
