import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        javaParameters = true
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
