package com.lukinhasssss.assinatura.domain.payment

fun interface PaymentGateway {
    fun processPayment(payment: Payment): Transaction
}
