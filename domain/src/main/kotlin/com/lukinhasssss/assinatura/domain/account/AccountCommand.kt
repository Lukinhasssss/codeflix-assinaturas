package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.person.Address
import com.lukinhasssss.assinatura.domain.person.Document
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name

sealed interface AccountCommand {
    data class ChangeProfileCommand(val aName: Name, val aBillingAddress: Address) : AccountCommand

    data class ChangeEmailCommand(val anEmail: Email) : AccountCommand

    data class ChangeDocumentCommand(val aDocument: Document) : AccountCommand
}
