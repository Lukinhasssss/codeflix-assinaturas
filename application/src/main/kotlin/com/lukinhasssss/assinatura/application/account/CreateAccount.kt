package com.lukinhasssss.assinatura.application.account

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.account.AccountId

abstract class CreateAccount : UseCase<CreateAccount.Input, CreateAccount.Output>() {
    interface Input {
        val userId: String
        val email: String
        val firstName: String
        val lastName: String
        val documentNumber: String
        val documentType: String
    }

    interface Output {
        val accountId: AccountId
    }
}
