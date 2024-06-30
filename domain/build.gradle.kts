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
    testImplementation(kotlin("test"))

    testImplementation("net.datafaker:datafaker:2.1.0")
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
