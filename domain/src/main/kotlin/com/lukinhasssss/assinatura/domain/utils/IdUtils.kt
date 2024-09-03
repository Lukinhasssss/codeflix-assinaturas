package com.lukinhasssss.assinatura.domain.utils

import java.util.UUID

object IdUtils {
    fun uuid() = UUID.randomUUID().toString().lowercase().replace("-", "")
}
