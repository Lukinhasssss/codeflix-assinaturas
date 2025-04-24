package com.lukinhasssss.assinatura.domain.account.idp

import com.lukinhasssss.assinatura.domain.Identifier

data class UserId(override val value: String) : Identifier {
    init {
        assertArgumentNotNull(value, "'userId' should not be null")
        assertArgumentNotEmpty(value, "'userId' should not be empty")
    }
}
