package com.lukinhasssss.assinatura.domain.account.idp

import com.lukinhasssss.assinatura.domain.AssertionConcern
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name

class User private constructor(
    val userId: UserId,
    val name: Name,
    val email: Email,
    val emailVerified: Boolean = false,
    val password: String? = null,
    val enabled: Boolean = true,
) : AssertionConcern {
    companion object {
        fun new(
            name: Name,
            email: Email,
            password: String,
        ): User {
            validatePassword(password)

            return User(
                userId = UserId.empty(),
                name = name,
                email = email,
                password = password,
            )
        }

        fun with(
            userId: UserId,
            name: Name,
            email: Email,
            emailVerified: Boolean,
            enabled: Boolean,
        ) = User(
            userId = userId,
            name = name,
            email = email,
            emailVerified = emailVerified,
            enabled = enabled,
        )
    }
}

private fun validatePassword(password: String) {
    if (password.isBlank()) {
        throw DomainException.with("User 'password' cannot not be empty for new users")
    }
}
