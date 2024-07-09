package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.admin.catalogo.domain.Identifier

data class UserId(override val value: String) : Identifier {
    init {
        assertArgumentNotNull(value, "'userId' should not be null")
        assertArgumentNotEmpty(value, "'userId' should not be empty")
    }
}
