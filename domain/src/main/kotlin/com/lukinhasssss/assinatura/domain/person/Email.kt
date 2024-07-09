package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.admin.catalogo.domain.ValueObject
import java.util.regex.Pattern

data class Email(val value: String) : ValueObject {
    private companion object {
        val EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)\$")
    }

    init {
        assertConditionTrue(EMAIL_REGEX.matcher(value).matches(), "'email' should be a valid email")
        assertArgumentIsAnEmail(value, "'email' should be a valid email")
    }
}
