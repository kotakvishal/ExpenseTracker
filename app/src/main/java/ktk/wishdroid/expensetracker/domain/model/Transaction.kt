package ktk.wishdroid.expensetracker.domain.model

data class Transaction(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: Category,
    val note: String? = null,
    val imageUri: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
