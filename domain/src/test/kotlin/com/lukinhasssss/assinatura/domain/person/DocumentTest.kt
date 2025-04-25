package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Document.Cnpj
import com.lukinhasssss.assinatura.domain.person.Document.Cpf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class DocumentTest : UnitTest, FunSpec({
    context("given valid document, when instantiate, should return ValueObject") {
        forAll(
            row("cpf", "12345678909"),
            row("cnpj", "12345678901234"),
        ) { expectedDocumentType, expectedDocumentNumber ->
            // when
            val actualDocument = DocumentFactory.create(expectedDocumentType, expectedDocumentNumber)

            // then
            actualDocument.value shouldBe expectedDocumentNumber
            when (expectedDocumentType) {
                "cpf" -> actualDocument.shouldBeInstanceOf<Cpf>()
                "cnpj" -> actualDocument.shouldBeInstanceOf<Cnpj>()
            }
        }
    }

    context("given empty document, when instantiate, should throws DomainException") {
        forAll(
            row("cpf"),
            row("cnpj"),
        ) { expectedDocumentType ->
            // given
            val expectedErrorMessage = "'$expectedDocumentType' should not be empty"

            // when
            val actualError =
                shouldThrow<DomainException> {
                    DocumentFactory.create(expectedDocumentType, "")
                }

            // then
            actualError.message shouldBe expectedErrorMessage
        }
    }

    context("given invalid document length, when instantiate, should throws DomainException") {
        forAll(
            row("cpf", "1234567890"),
            row("cnpj", "1234567890123"),
        ) { expectedDocumentType, expectedDocumentNumber ->
            // given
            val expectedErrorMessage = "'$expectedDocumentType' is invalid"

            // when
            val actualError =
                shouldThrow<DomainException> {
                    DocumentFactory.create(expectedDocumentType, expectedDocumentNumber)
                }

            // then
            actualError.message shouldBe expectedErrorMessage
        }
    }

    test("given invalid document type, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "Invalid document type"

        // when
        val actualError =
            shouldThrow<DomainException> {
                DocumentFactory.create("invalidType", "12345678909")
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
