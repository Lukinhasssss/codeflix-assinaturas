package com.lukinhasssss.assinatura.infrastructure.configuration

import com.lukinhasssss.assinatura.infrastructure.authentication.RefreshClientCredentials
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.lukinhasssss.assinatura")
@EnableScheduling
class WebServerConfig {
    @Bean
    @Profile(value = ["!test-integration && !test-e2e"])
    fun onAppStarted(refreshClientCredentials: RefreshClientCredentials) =
        ApplicationListener<ContextRefreshedEvent> { refreshClientCredentials.refresh() }
}
