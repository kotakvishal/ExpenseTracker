package ktk.wishdroid.expensetracker.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ktk.wishdroid.expensetracker.data.local.converters.CategoryConverter
import ktk.wishdroid.expensetracker.data.local.dao.TransactionsDao
import ktk.wishdroid.expensetracker.data.local.db.TransactionsDatabase
import ktk.wishdroid.expensetracker.data.local.repo.TransactionRepositoryImpl
import ktk.wishdroid.expensetracker.domain.repo.TransactionRepository
import ktk.wishdroid.expensetracker.domain.usecase.AddTransactionUseCase
import ktk.wishdroid.expensetracker.domain.usecase.DeleteTransactionUseCase
import ktk.wishdroid.expensetracker.domain.usecase.GetAllTransactionsUseCase
import ktk.wishdroid.expensetracker.domain.usecase.GetTransactionByIdUseCase
import ktk.wishdroid.expensetracker.domain.usecase.TransactionsUseCases
import ktk.wishdroid.expensetracker.domain.usecase.ValidateTransactionUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionsModule {
    @Provides
    @Singleton
    fun provideTransactionsDatabase(
        @ApplicationContext context: Context
    ): TransactionsDatabase = Room.databaseBuilder(
        context,
        TransactionsDatabase::class.java,
        "transactions_db"
    ).build()


    @Provides
    @Singleton
    fun provideTransactionsDao(db: TransactionsDatabase): TransactionsDao = db.transactionsDao()

    @Provides
    @Singleton
    fun provideTransactionsRepository(dao: TransactionsDao): TransactionRepository {
        return TransactionRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideTransactionUseCases(repository: TransactionRepository) = TransactionsUseCases(
        getAllTransactions = GetAllTransactionsUseCase(repository),
        getTransactionById = GetTransactionByIdUseCase(repository),
        insertTransaction = AddTransactionUseCase(repository),
        deleteTransaction = DeleteTransactionUseCase(repository),
        validateTransaction = ValidateTransactionUseCase()
    )
}