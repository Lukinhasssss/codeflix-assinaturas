package com.lukinhasssss.assinatura.application.account

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.account.idp.UserId

abstract class CreateIdpUser : UseCase<CreateIdpUser.Input, CreateIdpUser.Output>() {
    interface Input {
        val firstName: String
        val lastName: String
        val email: String
        val password: String
    }

    interface Output {
        val idpUserId: UserId
    }
}
