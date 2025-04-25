package com.lukinhasssss.assinatura.domain.payment

data class Transaction(
    val transactionId: String,
    val errorMessage: String? = null,
) {
    companion object {
        fun success(transactionId: String) = Transaction(transactionId)

        fun failure(
            transactionId: String,
            errorMessage: String,
        ) = Transaction(transactionId, errorMessage)
    }

    fun isSuccess() = errorMessage == null
}
