package com.lukinhasssss.assinatura.domain.validation

abstract class Validator(
    val validationHandler: ValidationHandler,
) {
    abstract fun validate()
}
