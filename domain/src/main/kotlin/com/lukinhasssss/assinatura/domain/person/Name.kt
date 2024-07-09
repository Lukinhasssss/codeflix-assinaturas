package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.admin.catalogo.domain.ValueObject

data class Name(val firstName: String, val lastName: String) : ValueObject {
    init {
        assertArgumentNotEmpty(firstName, "'firstName' should not be empty")
        assertArgumentNotEmpty(lastName, "'lastName' should not be empty")
    }

    fun fullName(): String = "$firstName $lastName"
}
