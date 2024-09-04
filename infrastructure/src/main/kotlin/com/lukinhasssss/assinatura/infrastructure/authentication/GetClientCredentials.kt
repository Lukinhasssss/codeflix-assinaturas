package com.lukinhasssss.assinatura.infrastructure.authentication

fun interface GetClientCredentials {
    fun retrieve(): String
}
