package ktk.wishdroid.expensetracker.data.mappers

import ktk.wishdroid.expensetracker.data.local.entities.CategoryEntity
import ktk.wishdroid.expensetracker.data.local.entities.TransactionEntity
import ktk.wishdroid.expensetracker.domain.model.Transaction

fun Transaction.toEntity() = TransactionEntity(
    id, title, amount, CategoryEntity.valueOf(category.name), note, imageUri, timestamp
)