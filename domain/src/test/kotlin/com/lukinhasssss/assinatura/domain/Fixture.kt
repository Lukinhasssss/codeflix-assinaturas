package com.lukinhasssss.assinatura.domain

import com.lukinhasssss.assinatura.domain.account.Account
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.account.idp.UserId
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.person.Address
import com.lukinhasssss.assinatura.domain.person.DocumentFactory
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanId
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import net.datafaker.Faker
import java.time.LocalDateTime
import java.time.ZoneOffset

object Fixture {
    private val FAKER = Faker()

    fun currency(): String = FAKER.currency().code()

    object Person {
        // Person
        fun firstName(): String = FAKER.name().firstName()

        fun lastName(): String = FAKER.name().lastName()

        fun fullName() = Name(firstName(), lastName())

        fun email(): String = FAKER.internet().emailAddress()

        fun emailClass() = Email(email())

        // Document
        fun cpf(): String = FAKER.cpf().valid(false)

        fun cnpj(): String = FAKER.cnpj().valid(false)

        fun document(type: String? = null) =
            when (type) {
                "cpf" -> DocumentFactory.createCpf(cpf())
                "cnpj" -> DocumentFactory.createCnpj(cnpj())
                else -> DocumentFactory.createCpf(cpf())
            }

        // Address
        fun zipCode(): String = FAKER.address().zipCode()

        fun buildingNumber(): String = FAKER.address().buildingNumber()

        fun complement(): String = FAKER.address().secondaryAddress()

        fun country(): String = FAKER.address().country()

        fun fullAddress() = Address(zipCode(), buildingNumber(), complement(), country())
    }

    object Plans {
        fun plus() =
            Plan.newPlan(
                aPlanId = PlanId(IdUtils.uuid()),
                aName = "Plus",
                aDescription = FAKER.lorem().sentence(),
                isActive = true,
                aPrice = Money(currency(), 32.90),
            )
    }

    object Accounts {
        fun john() =
            Account.newAccount(
                anAccountId = AccountId("ACC-123"),
                anUserId = UserId.from("USER-123"),
                aName = Person.fullName(),
                anEmail = Person.emailClass(),
                aDocument = Person.document(),
            )
    }

    object Subscriptions {
        fun with(
            accountId: AccountId,
            planId: PlanId,
            status: String,
            date: LocalDateTime,
        ): Subscription {
            val instant = date.toInstant(ZoneOffset.UTC)

            return Subscription.with(
                subscriptionId = SubscriptionId("SUB-123"),
                version = 1,
                accountId = accountId,
                planId = planId,
                status = status,
                lastTransactionId = "TID-123",
                dueDate = date.toLocalDate(),
                lastRenewDate = instant,
                createdAt = instant,
                updatedAt = instant,
            )
        }

        fun johns() =
            Subscription.new(
                anId = SubscriptionId("SUB-123"),
                anAccountId = Accounts.john().id,
                selectedPlan = Plans.plus(),
            )
    }
}
