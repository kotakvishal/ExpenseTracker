package ktk.wishdroid.expensetracker.presentation.ui.state

import ktk.wishdroid.expensetracker.domain.model.Category

data class AddTransactionUiState(
    val title: String = "",
    val amount: String = "",
    val notes: String = "",
    val category: Category = Category.STAFF,
    val expanded: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)