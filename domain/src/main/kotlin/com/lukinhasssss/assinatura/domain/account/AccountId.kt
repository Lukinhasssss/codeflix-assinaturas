package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.Identifier

data class AccountId(override val value: String) : Identifier {
    init {
        assertArgumentNotNull(value, "'accountId' should not be null")
        assertArgumentNotEmpty(value, "'accountId' should not be empty")
    }
}
