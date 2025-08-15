package ktk.wishdroid.expensetracker.data.mappers

import ktk.wishdroid.expensetracker.data.local.entities.TransactionEntity
import ktk.wishdroid.expensetracker.domain.model.Category
import ktk.wishdroid.expensetracker.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        title = title,
        amount = amount,
        category = Category.valueOf(category.name),
        note = note,
        imageUri = imageUri,
        timestamp = timestamp
    )
}
