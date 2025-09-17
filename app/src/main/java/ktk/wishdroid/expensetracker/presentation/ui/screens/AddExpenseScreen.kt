package ktk.wishdroid.expensetracker.presentation.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ktk.wishdroid.expensetracker.R
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.presentation.ui.components.CategoryDropdownField
import ktk.wishdroid.expensetracker.presentation.ui.components.InputField
import ktk.wishdroid.expensetracker.presentation.viewmodel.TransactionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onExpenseAdded: () -> Unit
) {
    val viewModel: TransactionsViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var receiptUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        receiptUri = uri
    }

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
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
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
            minHeight = 100.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)
                )
                .border(
                    1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)
                )
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (receiptUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(receiptUri),
                    contentDescription = "Receipt",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    "Tap to select receipt image",
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }

        Button(
            onClick = {
                viewModel.addTransaction(
                    Transaction(
                        title = state.title,
                        amount = state.amount.toDoubleOrNull() ?: 0.0,
                        category = state.category,
                        note = state.notes.takeIf { it.isNotBlank() },
                        imageUri = receiptUri?.toString()
                    ), { onExpenseAdded() })

            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Text("Add Expense")
        }
    }
}
