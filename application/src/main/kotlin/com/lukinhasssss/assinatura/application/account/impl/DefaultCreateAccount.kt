package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.account.CreateAccount
import com.lukinhasssss.assinatura.domain.account.Account
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.idp.UserId
import com.lukinhasssss.assinatura.domain.person.Document
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name

class DefaultCreateAccount(
    private val accountGateway: AccountGateway,
) : CreateAccount() {
    override fun execute(input: Input): Output {
        val anUserAccount = accountGateway.save(input.toNewAccount())
        return object : Output {
            override val accountId = anUserAccount.id
        }
    }

    private fun Input.toNewAccount() =
        Account.newAccount(
            anAccountId = accountGateway.nextId(),
            anUserId = UserId.from(userId),
            aName = Name(firstName, lastName),
            anEmail = Email(email),
            aDocument = Document.create(documentType, documentNumber),
        )
}
