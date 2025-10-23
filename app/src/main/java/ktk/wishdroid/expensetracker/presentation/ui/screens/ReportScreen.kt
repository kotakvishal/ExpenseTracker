package ktk.wishdroid.expensetracker.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ktk.wishdroid.expensetracker.presentation.ui.components.SimpleBarChart
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel

@Composable
fun ReportScreen() {
    val viewModel: TransactionsViewModel = hiltViewModel()
    val context = LocalContext.current

    val weeklyLabels = listOf("M","T","W","T","F","S","S")
    val weeklyValues by viewModel.weeklyReport.collectAsState()
    val categoryData by viewModel.categoryReport.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is TransactionsViewModel.UiEvent.ShowToast -> {
                    event.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Report", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Last 7 Days", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

        if (weeklyValues.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                weeklyValues.forEachIndexed { index, amount ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(weeklyLabels[index], fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text("₹${"%,.0f".format(amount)}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            val totalAmount = weeklyValues.sum()
            Text("Total: ₹${"%,.2f".format(totalAmount)}", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                SimpleBarChart(
                    values = weeklyValues,
                    labels = weeklyLabels,
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            Text("No transactions in the last 7 days", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }

        Text("By Category", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

        if (categoryData.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categoryData) { (category, amount) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(category, fontSize = 16.sp)
                        Text("₹${"%,.2f".format(amount)}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        } else {
            Text("No category data available", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }

        Button(
            onClick = { viewModel.exportTransactions() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Export")
        }
    }
}
