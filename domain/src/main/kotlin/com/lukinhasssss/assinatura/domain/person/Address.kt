package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.admin.catalogo.domain.ValueObject

data class Address(
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
