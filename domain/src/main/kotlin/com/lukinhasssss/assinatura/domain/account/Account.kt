package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.admin.catalogo.domain.AggregateRoot
import com.lukinhasssss.assinatura.domain.person.Address
import com.lukinhasssss.assinatura.domain.person.Document
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name

class Account private constructor(
    accountId: AccountId,
    val version: Int = 0,
    var userId: UserId,
    var name: Name,
    var email: Email,
    var document: Document,
    val billingAddress: Address? = null
) : AggregateRoot<AccountId>(accountId) {

    init {
        userId = assertArgumentNotNull(userId, "'userId' should not be null")
        email = assertArgumentNotNull(email, "'email' should not be null")
        name = assertArgumentNotNull(name, "'name' should not be null")
        document = assertArgumentNotNull(document, "'document' should not be null")
    }

    companion object {
        fun newAccount(
            anAccountId: AccountId,
            anUserId: UserId,
            aName: Name,
            anEmail: Email,
            aDocument: Document,
        ): Account =
            Account(accountId = anAccountId, userId = anUserId, name = aName, email = anEmail, document = aDocument)

        fun with(
            anAccountId: AccountId,
            version: Int,
            anUserId: UserId,
            aName: Name,
            anEmail: Email,
            aDocument: Document,
            billingAddress: Address? = null
        ): Account =
            Account(
                accountId = anAccountId,
                version = version,
                userId = anUserId,
                name = aName,
                email = anEmail,
                document = aDocument,
                billingAddress = billingAddress
            )
    }
}