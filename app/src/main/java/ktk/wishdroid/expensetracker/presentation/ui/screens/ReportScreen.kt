package ktk.wishdroid.expensetracker.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel

@Composable
fun ReportScreen(
    viewModel: TransactionsViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit
) {
    val total by viewModel.transactionsToday.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Report", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Total Expenses: ₹$total", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToList,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to List")
        }
    }
}
