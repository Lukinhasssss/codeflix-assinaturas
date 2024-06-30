package com.lukinhasssss.assinatura.application

import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext

@ExtendWith(MockKExtension::class)
@Tag(value = "unitTest")
abstract class UseCaseTest : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        clearAllMocks()
    }
}
