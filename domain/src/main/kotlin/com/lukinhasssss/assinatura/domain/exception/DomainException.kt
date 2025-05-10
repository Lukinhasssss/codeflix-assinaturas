package com.lukinhasssss.assinatura.domain.exception

import com.lukinhasssss.assinatura.domain.AggregateRoot
import com.lukinhasssss.assinatura.domain.Identifier
import com.lukinhasssss.assinatura.domain.validation.Error
import kotlin.reflect.KClass

open class DomainException(
    override val message: String?,
    val errors: List<Error>,
) : NoStacktraceException(message) {
    companion object {
        fun with(aMessage: String): DomainException {
            return DomainException(message = aMessage, errors = listOf(Error(message = aMessage)))
        }

        fun with(anError: Error): DomainException {
            return DomainException(message = anError.message, errors = listOf(anError))
        }

        fun with(anErrors: List<Error>): DomainException {
            return DomainException(message = "", errors = anErrors)
        }

        fun notFound(
            aggClass: KClass<out AggregateRoot<*>>,
            id: Identifier,
        ): DomainException {
            return with("${aggClass.simpleName} with id ${id.value} was not found")
        }
    }
}
