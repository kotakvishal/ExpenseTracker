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
    // Sample weekly data
    val weeklyValues = listOf(1500f, 2200f, 1800f, 2000f, 3000f, 2500f, 2700f)
    val weeklyLabels = listOf("M","T","W","T","F","S","S")

    val viewModel: TransactionsViewModel = hiltViewModel()
    val categoryData = listOf(
        "Staff" to 1500.0,
        "Travel" to 3750.0,
        "Food" to 2250.0,
        "Utility" to 750.0
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is TransactionsViewModel.UiEvent.ShowToast -> {
                    event.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }

                is TransactionsViewModel.UiEvent.TransactionAdded -> {
                    //Note for reviewer: This wont be called (we can separate events class for report screen but as it is a small app we can use same for both.)
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            weeklyValues.forEachIndexed { index, amount ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weeklyLabels[index],
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "₹${"%,.0f".format(amount)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // --- Total Last 7 Days ---
        val totalAmount = weeklyValues.sum()
        Text(
            "Total: ₹${"%,.2f".format(totalAmount)}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        // --- Bar Chart ---
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

        // --- By Category ---
        Text("By Category", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

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

        // --- Export Button ---
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
