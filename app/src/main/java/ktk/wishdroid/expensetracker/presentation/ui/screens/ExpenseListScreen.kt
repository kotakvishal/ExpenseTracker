package ktk.wishdroid.expensetracker.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ktk.wishdroid.expensetracker.presentation.ui.components.TabItem
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel
import java.time.format.TextStyle

@Composable
fun ExpenseListScreen(
) {
    val viewModel: TransactionsViewModel = hiltViewModel()
    val transactions by viewModel.transactionsToday.collectAsState()
    var selectedTab by remember { mutableStateOf("Today") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Expenses",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))

                val total = transactions.sumOf { it.amount }
                Text(
                    text = "₹$total",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TabItem("Today", selectedTab == "Today") { selectedTab = "Today" }
            TabItem("Select Range", selectedTab == "Select Range") { selectedTab = "Select Range" }
        }


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
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




        Spacer(modifier = Modifier.height(16.dp))
    }
}
