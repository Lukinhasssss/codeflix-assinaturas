package com.lukinhasssss.assinatura.application

abstract class UseCase<IN, OUT> {
    abstract fun execute(input: IN): OUT

    fun <T> execute(
        input: IN,
        presenter: Presenter<OUT, T>?,
    ): T {
        require(presenter != null) { "Usecase 'presenter' is required" }
        return presenter.invoke(execute(input))
    }
}
