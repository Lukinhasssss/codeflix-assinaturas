package com.lukinhasssss.assinatura.domain.money

import com.lukinhasssss.assinatura.domain.ValueObject
import java.util.Currency

data class MonetaryAmount(
    val currency: Currency,
    val amount: Double,
) : ValueObject {
    init {
        assertConditionTrue(amount >= 0, "'amount' should be greater than or equal to 0")
    }

    constructor(currency: String, amount: Double) : this(Currency.getInstance(currency), amount)
}