package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.account.RemoveFromGroup
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.idp.GroupId
import com.lukinhasssss.assinatura.domain.account.idp.IdentityProviderGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class DefaultRemoveFromGroupTest : UseCaseTest, FunSpec({
    val identityProviderGateway = mockk<IdentityProviderGateway>()
    val subscriptionGateway = mockk<SubscriptionGateway>()
    val accountGateway = mockk<AccountGateway>()

    val sut =
        DefaultRemoveFromGroup(
            identityProviderGateway = identityProviderGateway,
            subscriptionGateway = subscriptionGateway,
            accountGateway = accountGateway,
        )

    test("given canceled subscription and due date in the past, when calls execute, should calls identity provider") {
        // given
        val planPlus = Fixture.Plans.plus()
        val john = Fixture.Accounts.john()
        val expectedAccountId = john.id
        val expectedGroupId = GroupId.from("GROUP-123")
        val johnsSubscription =
            Fixture.Subscriptions.with(
                accountId = expectedAccountId,
                planId = planPlus.id,
                status = SubscriptionStatus.CANCELED,
                date = LocalDateTime.now().minusDays(1),
            )
        val expectedSubscriptionId = johnsSubscription.id

        val input =
            object : RemoveFromGroup.Input {
                override val accountId = expectedAccountId.value
                override val groupId = expectedGroupId.value
                override val subscriptionId = expectedSubscriptionId.value
            }

        withClue("Para esse teste a subscription precisa estar como canceled") {
            johnsSubscription.isCanceled().shouldBeTrue()
        }

        justRun { identityProviderGateway.removeUserFromGroup(any(), any()) }
        every { subscriptionGateway.subscriptionOfId(any()) } returns johnsSubscription
        every { accountGateway.accountOfId(any()) } returns Fixture.Accounts.john()

        // when
        sut.execute(input)

        // then
        verify { identityProviderGateway.removeUserFromGroup(john.userId, expectedGroupId) }
        verify { subscriptionGateway.subscriptionOfId(expectedSubscriptionId) }
        verify { accountGateway.accountOfId(expectedAccountId) }
    }
})
