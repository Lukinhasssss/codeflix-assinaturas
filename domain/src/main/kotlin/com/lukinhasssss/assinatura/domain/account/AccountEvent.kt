package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.admin.catalogo.domain.event.DomainEvent
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import java.time.Instant

sealed interface AccountEvent : DomainEvent {
    companion object {
        val TYPE = Account::class.simpleName!!
    }

    val accountId: String

    override val aggregateId: String
        get() = accountId

    override val aggregateType: String
        get() = TYPE

    data class AccountCreated(
        override val accountId: String,
        val email: String,
        val fullName: String,
        override val occurredOn: Instant,
    ) : AccountEvent {
        init {
            assertArgumentNotEmpty(accountId, "'accountId' should not be empty")
            assertArgumentNotEmpty(email, "'email' should not be empty")
            assertArgumentNotEmpty(fullName, "'fullName' should not be empty")
        }

        constructor(anAccount: Account) : this(
            accountId = anAccount.id.value,
            email = anAccount.email.value,
            fullName = anAccount.name.fullName(),
            occurredOn = InstantUtils.now(),
        )
    }
}
