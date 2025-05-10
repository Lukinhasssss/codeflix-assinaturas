package com.lukinhasssss.assinatura.domain.payment

import com.lukinhasssss.assinatura.domain.AssertionConcern
import com.lukinhasssss.assinatura.domain.exception.DomainException

sealed interface Payment : AssertionConcern {
    companion object {
        const val PIX = "pix"
        const val CREDIT_CARD = "credit_card"

        fun create(
            type: String,
            amount: Double,
            orderId: String,
            address: BillingAddress,
            token: String? = null,
        ): Payment {
            return when (type) {
                PIX -> Pix(amount, orderId, address)
                CREDIT_CARD -> CreditCard(amount, orderId, address, token!!)
                else -> throw DomainException.with("Invalid payment type: $type")
            }
        }
    }

    val amount: Double
    val orderId: String
    val address: BillingAddress

    data class Pix(
        override val amount: Double,
        override val orderId: String,
        override val address: BillingAddress,
    ) : Payment {
        init {
            assertConditionTrue(amount > 0, "Payment 'amount' should be greater than 0")
            assertArgumentNotEmpty(orderId, "Payment 'orderId' should not be empty")
        }
    }

    data class CreditCard(
        override val amount: Double,
        override val orderId: String,
        override val address: BillingAddress,
        val token: String,
    ) : Payment {
        init {
            assertConditionTrue(amount > 0, "Payment 'amount' should be greater than 0")
            assertArgumentNotEmpty(orderId, "Payment 'orderId' should not be empty")
            assertArgumentNotEmpty(token, "Payment 'token' should not be empty")
        }
    }
}
