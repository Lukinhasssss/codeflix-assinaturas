package com.lukinhasssss.assinatura.application

abstract class UnitUseCase<IN> {
    abstract fun execute(input: IN)
}
