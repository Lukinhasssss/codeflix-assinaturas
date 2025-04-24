package com.lukinhasssss.assinatura.domain.account.idp

import com.lukinhasssss.assinatura.domain.Identifier
import com.lukinhasssss.assinatura.domain.exception.DomainException

@ConsistentCopyVisibility
data class UserId private constructor(override val value: String) : Identifier {
    companion object {
        fun from(value: String): UserId {
            if (value.isBlank()) {
                throw DomainException.with("'userId' should not be empty")
            }

            return UserId(value)
        }

        fun empty() = UserId("")
    }
}
