package com.lukinhasssss.assinatura.domain.account.idp

import com.lukinhasssss.assinatura.domain.Identifier

@ConsistentCopyVisibility
data class GroupId private constructor(override val value: String) : Identifier {
    init {
        assertArgumentNotEmpty(value, "'groupId' should not be empty")
    }

    companion object {
        fun from(value: String) = GroupId(value)
    }
}
