import org.gradle.kotlin.dsl.test

plugins {
    id("java-conventions")
    kotlin("jvm") version Version.KOTLIN
    id("jacoco")
    id("org.sonarqube") version Version.SONARQUBE
}

group = "com.lukinhasssss.assinatura.domain"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("net.datafaker:datafaker:2.1.0")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:${Version.KOTEST}")
}

configurations {
    create("testClasses") {
        extendsFrom(testImplementation.get())
    }
}

tasks.getByName("assemble").dependsOn("testJar")

tasks.register<Jar>("testJar") {
    archiveClassifier.set("test")
    from(project.the<SourceSetContainer>()["test"].output)
}

artifacts {
    testImplementation(tasks.getByName("testJar"))
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
