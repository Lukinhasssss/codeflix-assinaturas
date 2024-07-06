package com.lukinhasssss.assinatura.domain

import com.lukinhasssss.assinatura.domain.person.Address
import com.lukinhasssss.assinatura.domain.person.DocumentFactory
import com.lukinhasssss.assinatura.domain.person.Name
import net.datafaker.Faker

object Fixture {
    private val FAKER = Faker()

    object Person {
        // Person
        fun firstName() = FAKER.name().firstName()

        fun lastName() = FAKER.name().lastName()

        fun fullName() = Name(firstName(), lastName())

        fun email() = FAKER.internet().emailAddress()

        // Document
        fun cpf() = FAKER.cpf().valid()

        fun cnpj() = FAKER.cnpj().valid()

        fun document(type: String? = null) =
            when (type) {
                "cpf" -> DocumentFactory.createCpf(cpf())
                "cnpj" -> DocumentFactory.createCnpj(cnpj())
                else -> DocumentFactory.createCpf(cpf())
            }

        // Address
        fun zipCode() = FAKER.address().zipCode()

        fun buildingNumber() = FAKER.address().buildingNumber()

        fun complement() = FAKER.address().secondaryAddress()

        fun country() = FAKER.address().country()

        fun fullAddress() = Address(zipCode(), buildingNumber(), complement(), country())
    }
}
