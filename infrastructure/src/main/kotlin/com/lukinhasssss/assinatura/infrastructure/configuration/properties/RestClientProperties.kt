package com.lukinhasssss.assinatura.infrastructure.configuration.properties

data class RestClientProperties(
    var baseUrl: String = String(),
    var readTimeout: Int = 0,
)
