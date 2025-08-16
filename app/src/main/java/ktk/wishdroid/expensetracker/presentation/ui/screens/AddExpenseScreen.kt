package ktk.wishdroid.expensetracker.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ktk.wishdroid.expensetracker.R
import ktk.wishdroid.expensetracker.domain.model.Category
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.presentation.ui.components.CategoryDropdownField
import ktk.wishdroid.expensetracker.presentation.ui.components.InputField
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen() {
    val viewModel: TransactionsViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        InputField(
            value = state.title,
            onValueChange = viewModel::onTitleChanged,
            label = "Title",
            placeholder = "Enter expense title",
            modifier = Modifier.fillMaxWidth()
        )

        InputField(
            value = state.amount,
            onValueChange = viewModel::onAmountChanged,
            label = "Amount (₹)",
            placeholder = "Enter amount",
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth()
        )

        CategoryDropdownField(
            selectedCategory = state.category,
            expanded = state.expanded,
            onExpandedChange = { viewModel.toggleExpanded() },
            onCategorySelected = { viewModel.onCategorySelected(it) }
        )

        InputField(
            value = state.notes,
            onValueChange = viewModel::onNotesAdded,
            label = "Optional notes (max 100 chars)",
            placeholder = "Add extra details",
            singleLine = false,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        if (!state.error.isNullOrEmpty()) {
            Text(
                text = state.error ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

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
            onClick = { viewModel.addTransaction(Transaction(
                title = state.title,
                amount = state.amount.toDoubleOrNull() ?: 0.0,
                category = state.category,
                note = state.notes.takeIf { it.isNotBlank() },
                imageUri = null
            )) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Add Expense")
        }
    }
}
