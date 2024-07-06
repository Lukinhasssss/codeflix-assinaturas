package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Document.Cnpj
import com.lukinhasssss.assinatura.domain.person.Document.Cpf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class DocumentTest : UnitTest {
    @ParameterizedTest
    @CsvSource(
        "cpf, 12345678909",
        "cnpj, 12345678901234",
    )
    fun `given valid document, when instantiate, should return ValueObject`(
        expectedDocumentType: String,
        expectedDocumentNumber: String,
    ) {
        // when
        val actualDocument = DocumentFactory.create(expectedDocumentType, expectedDocumentNumber)

        // then
        assertEquals(expectedDocumentNumber, actualDocument.value)
        when (expectedDocumentType) {
            "cpf" -> assert(actualDocument is Cpf)
            "cnpj" -> assert(actualDocument is Cnpj)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["cpf", "cnpj"])
    fun `given empty document, when instantiate, should throws DomainException`(expectedDocumentType: String) {
        // given
        val expectedErrorMessage = "'$expectedDocumentType' should not be empty"

        // when
        val actualError =
            assertThrows<DomainException> {
                DocumentFactory.create(expectedDocumentType, "")
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @ParameterizedTest
    @CsvSource(
        "cpf, 1234567890",
        "cnpj, 1234567890123",
    )
    fun `given invalid document lenght, when instantiate, should throws DomainException`(
        expectedDocumentType: String,
        expectedDocumentNumber: String,
    ) {
        // given
        val expectedErrorMessage = "'$expectedDocumentType' is invalid"

        // when
        val actualError =
            assertThrows<DomainException> {
                DocumentFactory.create(expectedDocumentType, expectedDocumentNumber)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given invalid document type, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "Invalid document type"

        // when
        val actualError =
            assertThrows<DomainException> {
                DocumentFactory.create("invalidType", "12345678909")
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}
