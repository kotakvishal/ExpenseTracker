package ktk.wishdroid.expensetracker.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ktk.wishdroid.expensetracker.presentation.ui.components.SimpleBarChart

@Composable
fun ReportScreen(
    onExportClick: () -> Unit = {}
) {
    val weeklyValues = listOf(1500f, 2200f, 1800f, 2000f, 3000f, 2500f, 2700f)
    val weeklyLabels = listOf("M","T","W","T","F","S","S")

    val categoryData = listOf(
        "Staff" to 1500.0,
        "Travel" to 3750.0,
        "Food" to 2250.0,
        "Utility" to 750.0
    )

    val totalAmount = categoryData.sumOf { it.second }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Report", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Last 7 Days", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text("₹${"%,.2f".format(totalAmount)}", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        // Bar Chart
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
        ) {
            SimpleBarChart(
                values = weeklyValues,
                labels = weeklyLabels,
                modifier = Modifier.fillMaxSize()
            )
        }

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

        Button(
            onClick = onExportClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Export")
        }
    }
}
