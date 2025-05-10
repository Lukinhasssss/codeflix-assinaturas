package com.lukinhasssss.assinatura.domain.payment

import com.lukinhasssss.assinatura.domain.ValueObject

data class BillingAddress(
    val zipCode: String,
    val number: String,
    val complement: String = "",
    val country: String,
) : ValueObject {
    init {
        assertArgumentNotEmpty(zipCode, "'zipCode' should not be empty")
        assertArgumentNotEmpty(number, "'number' should not be empty")
        assertArgumentNotEmpty(country, "'country' should not be empty")
    }
}
