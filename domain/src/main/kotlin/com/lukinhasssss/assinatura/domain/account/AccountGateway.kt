package com.lukinhasssss.assinatura.domain.account

interface AccountGateway {
    fun nextId(): AccountId

    fun accountOfId(id: AccountId): Account?

    fun save(account: Account): Account
}
