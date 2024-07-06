package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.admin.catalogo.domain.ValueObject

data class Email(val value: String) : ValueObject {
    init {
        assertArgumentNotEmpty(value, "'email' should not be empty")
    }
}
