package com.lukinhasssss.assinatura.infrastructure

import com.lukinhasssss.assinatura.infrastructure.configuration.WebServerConfig
import com.lukinhasssss.assinatura.infrastructure.utils.Logger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME

@SpringBootApplication
class CatalogoDeVideos

fun main(args: Array<String>) {
    System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "development")
    SpringApplication.run(WebServerConfig::class.java, *args)

    Logger.info(message = "Microsserviço de Catálogo de Vídeos inicializado com sucesso")
}
