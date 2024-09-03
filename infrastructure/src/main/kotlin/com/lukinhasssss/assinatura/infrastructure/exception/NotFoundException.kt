package com.lukinhasssss.assinatura.infrastructure.exception

import com.lukinhasssss.assinatura.domain.exception.InternalErrorException

data class NotFoundException(override val message: String) : InternalErrorException(message) {
    companion object {
        fun with(message: String) = NotFoundException(message = message)
    }
}
