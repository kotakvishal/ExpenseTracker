package ktk.wishdroid.expensetracker.data.local.repo

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ktk.wishdroid.expensetracker.data.local.dao.TransactionsDao
import ktk.wishdroid.expensetracker.domain.model.Transaction
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository
import javax.inject.Inject
import ktk.wishdroid.expensetracker.data.mappers.toDomain
import ktk.wishdroid.expensetracker.data.mappers.toEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao,
    private val context : Context
) : TransactionRepository {

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction.toEntity())
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTransactionById(id: Long): Flow<Transaction?> {
        return dao.getTransactionById(id).map { it?.toDomain() }
    }
    override suspend fun exportTransactionsToCsv(): Boolean {
        return try {
            val transactions = dao.getAllTransactions().first() // gets current snapshot
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val csv = buildString {
                append("Title,Amount,Category,Notes,Date\n")
                transactions.forEach {
                    val date = sdf.format(Date(it.timestamp))
                    append("${it.title},${it.amount},${it.category},${it.note ?: ""},$date\n")
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, "transactions${System.currentTimeMillis()}.csv")
                    put(MediaStore.Downloads.MIME_TYPE, "text/csv")
                    put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                uri?.let {
                    resolver.openOutputStream(it)?.use { outputStream ->
                        outputStream.write(csv.toByteArray())
                    }
                }
            } else {
                val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsFolder.exists()) downloadsFolder.mkdirs()
                val file = File(downloadsFolder, "transactions${System.currentTimeMillis()}.csv")
                file.writeText(csv)
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
