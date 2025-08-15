package ktk.wishdroid.expensetracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ktk.wishdroid.expensetracker.data.local.converters.CategoryConverter
import ktk.wishdroid.expensetracker.data.local.dao.TransactionsDao
import ktk.wishdroid.expensetracker.data.local.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
@TypeConverters(CategoryConverter::class)
abstract class TransactionsDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
}