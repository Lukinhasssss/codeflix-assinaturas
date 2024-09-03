package com.lukinhasssss.assinatura.application

abstract class UseCase<IN, OUT> {
    abstract fun execute(input: IN): OUT
}
