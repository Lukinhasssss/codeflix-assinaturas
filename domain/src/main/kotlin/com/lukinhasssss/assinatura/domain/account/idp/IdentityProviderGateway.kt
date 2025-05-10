package com.lukinhasssss.assinatura.domain.account.idp

interface IdentityProviderGateway {
    fun create(anUser: User): UserId

    fun addUserToGroup(
        anId: UserId,
        aGroupId: GroupId,
    )

    fun removeUserFromGroup(
        anId: UserId,
        aGroupId: GroupId,
    )
}
