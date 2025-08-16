package ktk.wishdroid.expensetracker.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ktk.wishdroid.expensetracker.presentation.ui.components.TabItem
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    onAddTransactionClick: () -> Unit // 👈 callback for navigation
) {
    val viewModel: TransactionsViewModel = hiltViewModel()
    val transactions by viewModel.transactions.collectAsState()
    var selectedTab by remember { mutableStateOf("today") }
    var showDateRangePicker by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddTransactionClick() }, // 👈 pass navigation event
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->   // 👈 Scaffold ke padding

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp) // sirf side padding manually
        ) {
            // --- Tabs ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TabItem(title = "Today", label = "today", selectedTab == "today") {
                    selectedTab = "today"
                    viewModel.getTodayAllTransaction()
                }
                TabItem(title = "Select Range", label = "select_range", selectedTab == "select_range") {
                    showDateRangePicker = true
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Date Range Picker Dialog ---
            if (showDateRangePicker) {
                val dateRangePickerState = rememberDateRangePickerState()

                DatePickerDialog(
                    onDismissRequest = { showDateRangePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDateRangePicker = false
                            selectedTab = "select_range"
                            val start = dateRangePickerState.selectedStartDateMillis
                            val end = dateRangePickerState.selectedEndDateMillis
                            if (start != null && end != null) {
                                viewModel.getTransactionsByDateRange(start, end)
                            }
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDateRangePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Select Date Range",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        DateRangePicker(
                            state = dateRangePickerState,
                            showModeToggle = false,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = paddingValues.calculateBottomPadding() + 16.dp // 👈 bas thoda extra
                )
            ) {
                items(transactions) { transaction ->
                    Column {
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = transaction.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = transaction.category.displayName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light
                                )
                            }
                            Text(
                                text = "₹${transaction.amount}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                    }
                }
            }

        }
    }
}
