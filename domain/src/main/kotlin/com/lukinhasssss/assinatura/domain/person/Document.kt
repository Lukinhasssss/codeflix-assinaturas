package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.admin.catalogo.domain.ValueObject

interface Document : ValueObject {
    val type: String
    val value: String

    data class Cpf(
        override val type: String = TYPE,
        override val value: String,
    ) : Document {
        init {
            assertArgumentNotEmpty(value, "'cpf' should not be empty")
            assertArgumentExactlyLength(value, CPF_LENGTH, "'cpf' is invalid")
        }

        companion object {
            const val TYPE = "cpf"
        }
    }

    data class Cnpj(
        override val type: String = TYPE,
        override val value: String,
    ) : Document {
        init {
            assertArgumentNotEmpty(value, "'cnpj' should not be empty")
            assertArgumentExactlyLength(value, CNPJ_LENGTH, "'cnpj' is invalid")
        }

        companion object {
            const val TYPE = "cnpj"
        }
    }

    companion object {
        private const val CPF_LENGTH = 11
        private const val CNPJ_LENGTH = 14

        fun create(
            documentType: String,
            documentNumber: String,
        ): Document = DocumentFactory.create(documentType, documentNumber)
    }
}
