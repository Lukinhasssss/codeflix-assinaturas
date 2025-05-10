package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.account.CreateIdpUser
import com.lukinhasssss.assinatura.domain.account.idp.IdentityProviderGateway
import com.lukinhasssss.assinatura.domain.account.idp.User
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name

class DefaultCreateIdpUser(
    private val identityProviderGateway: IdentityProviderGateway,
) : CreateIdpUser() {
    override fun execute(input: Input): Output {
        val userId = identityProviderGateway.create(input.toNewUser())
        return object : Output {
            override val idpUserId = userId
        }
        // return StdOutput(userId)
    }

    private fun Input.toNewUser() =
        User.new(
            name = Name(firstName, lastName),
            email = Email(email),
            password = password,
        )

    // data class StdOutput(
    //     override val idpUserId: UserId
    // ) : Output
}
