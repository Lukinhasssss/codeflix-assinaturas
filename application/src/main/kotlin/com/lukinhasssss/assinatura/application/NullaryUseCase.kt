package com.lukinhasssss.assinatura.application

abstract class NullaryUseCase<OUT> {
    abstract fun execute(): OUT

    fun <T> execute(presenter: Presenter<OUT, T>?): T {
        require(presenter != null) { "NullaryUseCase 'presenter' is required" }
        return presenter.invoke(execute())
    }
}
