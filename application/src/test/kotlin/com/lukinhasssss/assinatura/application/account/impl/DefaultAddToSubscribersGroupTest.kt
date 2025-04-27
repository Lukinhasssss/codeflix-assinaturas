package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.account.AddToSubscribersGroup
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.idp.GroupId
import com.lukinhasssss.assinatura.domain.account.idp.IdentityProviderGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify

class DefaultAddToSubscribersGroupTest : UseCaseTest, FunSpec({
    val identityProviderGateway = mockk<IdentityProviderGateway>()
    val subscriptionGateway = mockk<SubscriptionGateway>()
    val accountGateway = mockk<AccountGateway>()

    val sut =
        DefaultAddToSubscribersGroup(
            identityProviderGateway = identityProviderGateway,
            subscriptionGateway = subscriptionGateway,
            accountGateway = accountGateway,
        )

    test("given trialing, when calls execute, should calls identity provider") {
        // given
        val john = Fixture.Accounts.john()
        val expectedAccountId = john.id
        val expectedSubscriptionId = SubscriptionId("SUB-123")
        val expectedGroupId = GroupId.from("GROUP-123")
        val johnsSubscription = Fixture.Subscriptions.johns()

        val input =
            object : AddToSubscribersGroup.Input {
                override val accountId = expectedAccountId.value
                override val groupId = expectedGroupId.value
                override val subscriptionId = expectedSubscriptionId.value
            }

        withClue("Para esse teste a subscription precisa estar como trialing") {
            johnsSubscription.isTrial().shouldBeTrue()
        }

        justRun { identityProviderGateway.addUserToGroup(any(), any()) }
        every { subscriptionGateway.subscriptionOfId(any()) } returns johnsSubscription
        every { accountGateway.accountOfId(any()) } returns Fixture.Accounts.john()

        // when
        sut.execute(input)

        // then
        verify { identityProviderGateway.addUserToGroup(john.userId, expectedGroupId) }
        verify { subscriptionGateway.subscriptionOfId(expectedSubscriptionId) }
        verify { accountGateway.accountOfId(expectedAccountId) }
    }
})
