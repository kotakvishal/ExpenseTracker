package ktk.wishdroid.expensetracker.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ktk.wishdroid.expensetracker.domain.model.Category
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import ktk.wishdroid.expensetracker.presentation.ui.components.EditTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModel: TransactionsViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(Category.STAFF) }
    var note by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.headlineSmall
        )

        EditTextField(
            value = title,
            onValueChange = { title = it },
            label = "Title",
            placeholder = "Enter expense title",
            modifier = Modifier.fillMaxWidth()
        )

        EditTextField(
            value = amount,
            onValueChange = { amount = it },
            label = "Amount (₹)",
            placeholder = "Enter amount",
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Category.entries.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = {
                            category = it
                            expanded = false
                        }
                    )
                }
            }
        }

        EditTextField(
            value = note,
            onValueChange = { note = it },
            label = "Optional notes (max 100 chars)",
            placeholder = "Add extra details",
            singleLine = false,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Receipt image", color = Color.Gray, textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.addTransaction(
                    Transaction(
                        title = title,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        category = category,
                        note = note.takeIf { it.isNotBlank() },
                        imageUri = null
                    )
                )
                onNavigateToList()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Add Expense")
        }
    }
}
