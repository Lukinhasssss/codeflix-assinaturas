package com.lukinhasssss.assinatura.infrastructure.configuration.annotations

import org.springframework.beans.factory.annotation.Qualifier

@Qualifier("Keycloak")
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Keycloak
