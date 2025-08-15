package ktk.wishdroid.expensetracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: CategoryEntity,
    val note: String? = null,
    val imageUri: String? = null,
    val timestamp: Long
)
