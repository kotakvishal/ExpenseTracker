package ktk.wishdroid.expensetracker.domain.usecase

import ktk.wishdroid.expensetracker.domain.model.Transaction


class ValidateTransactionUseCase {
    operator fun invoke(transaction: Transaction): ValidationResult {
        if(transaction.title.isBlank() && transaction.amount <= 0) {
            return ValidationResult(false, "Please enter a valid title and amount")
        }
        if (transaction.title.isBlank()) return ValidationResult(false, "Title cannot be empty")
        if (transaction.amount <= 0) return ValidationResult(false, "Amount must be greater than 0")
        return ValidationResult(true)
    }
}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
