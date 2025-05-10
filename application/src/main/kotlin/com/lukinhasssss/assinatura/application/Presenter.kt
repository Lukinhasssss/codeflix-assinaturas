package com.lukinhasssss.assinatura.application

fun interface Presenter<UC_OUT, NEW_OUT> : (UC_OUT) -> NEW_OUT
