package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.exception.DomainException

object DocumentFactory {
    fun create(
        documentType: String,
        documentNumber: String,
    ): Document {
        return when (documentType) {
            Document.Cpf.TYPE -> Document.Cpf(value = documentNumber)
            Document.Cnpj.TYPE -> Document.Cnpj(value = documentNumber)
            else -> throw DomainException.with("Invalid document type")
        }
    }

    fun createCpf(documentNumber: String): Document.Cpf {
        return Document.Cpf(value = documentNumber)
    }

    fun createCnpj(documentNumber: String): Document.Cnpj {
        return Document.Cnpj(value = documentNumber)
    }
}
