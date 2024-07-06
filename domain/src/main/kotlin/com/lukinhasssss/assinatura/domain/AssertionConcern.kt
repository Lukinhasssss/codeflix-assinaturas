package com.lukinhasssss.assinatura.domain

import com.lukinhasssss.assinatura.domain.exception.DomainException

interface AssertionConcern {
    fun <T> assertArgumentNotNull(
        value: T,
        aMessage: String,
    ): T {
        if (value == null) {
            throw DomainException.with(aMessage)
        }
        return value
    }

    fun assertArgumentNotEmpty(
        value: String,
        aMessage: String,
    ): String {
        if (value.isBlank()) {
            throw DomainException.with(aMessage)
        }
        return value
    }

    fun assertArgumentExactlyLength(
        value: String,
        length: Int,
        aMessage: String,
    ): String {
        if (value.length != length) {
            throw DomainException.with(aMessage)
        }
        return value
    }
}
