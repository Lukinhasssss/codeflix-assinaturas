package com.lukinhasssss.assinatura.infrastructure.configuration.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.blackbird.BlackbirdModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.util.concurrent.Callable

private const val REFLECTION_CACHE_SIZE = 512

enum class Json {
    INSTANCE,
    ;

    companion object {
        fun mapper(): ObjectMapper = INSTANCE.mapper.copy()

        fun writeValueAsString(obj: Any): String {
            return invoke { INSTANCE.mapper.writeValueAsString(obj) }
        }

        fun <T> readValue(
            json: String,
            clazz: Class<T>,
        ): T {
            return invoke { INSTANCE.mapper.readValue(json, clazz) }
        }

        fun <T> readValue(
            json: String,
            clazz: TypeReference<T>,
        ): T {
            return invoke { INSTANCE.mapper.readValue(json, clazz) }
        }

        private operator fun <T> invoke(callable: Callable<T>): T {
            return try {
                callable.call()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

    private val mapper: ObjectMapper =
        Jackson2ObjectMapperBuilder()
            .dateFormat(StdDateFormat())
            .featuresToDisable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            )
            .modules(
                JavaTimeModule(),
                Jdk8Module(),
                BlackbirdModule(),
                KotlinModule.Builder()
                    .withReflectionCacheSize(REFLECTION_CACHE_SIZE)
                    .configure(KotlinFeature.NullToEmptyCollection, true)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, false)
                    .configure(KotlinFeature.SingletonSupport, false)
                    .configure(KotlinFeature.StrictNullChecks, false)
                    .build(),
            )
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            // .serializationInclusion(JsonInclude.Include.NON_EMPTY)
            .build()
}

/**
 * FAIL_ON_UNKNOWN_PROPERTIES: Se vier uma propriedade que meu objeto não mapeou, eu quero simplesmente ignorar
 * FAIL_ON_NULL_FOR_PRIMITIVES: Se vier null para uma propriedade primitiva, eu quero simplesmente ignorar
 *
 * JavaTimeModule: Este modulo faz com que o ObjectMapper entenda como serializar e deserializar datas
 * que vieram do package JavaTime do Java8
 * Jdk8Module: Este modulo faz com que o Optional entenda como serializar e deserializar com optionals
 */
