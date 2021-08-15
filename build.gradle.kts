import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    java
    `kotlin-dsl`
    distribution
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

object BuildSettings {
    const val mainClass = "io.ktor.server.netty.EngineMain"
    const val archiveBaseName = "application"
    const val archiveFileName = "application.jar"

    object Versions {
        const val dropWizard = "4.2.3"
        const val kodein = "7.7.0"
        const val kotlin = "1.5.21"
        const val kotlinCoroutines = "1.5.1"
        const val kotlinImmutable = "0.3.4"
        const val ktor = "1.6.2"
        const val slack = "1.3.2"
    }
}

application {
    mainClass.set(BuildSettings.mainClass)
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${BuildSettings.Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${BuildSettings.Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildSettings.Versions.kotlinCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:${BuildSettings.Versions.kotlinImmutable}")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${BuildSettings.Versions.kotlin}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${BuildSettings.Versions.kotlin}")

    // ktor
    implementation("io.ktor:ktor:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-jackson:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-auth:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-auth-jwt:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-metrics:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-html-builder:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-server-test-host:${BuildSettings.Versions.ktor}")
    implementation("io.ktor:ktor-server-sessions:${BuildSettings.Versions.ktor}")

    // kodein
    implementation("org.kodein.di:kodein-di:${BuildSettings.Versions.kodein}")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:${BuildSettings.Versions.kodein}")
    implementation("org.kodein.di:kodein-di-conf-jvm:${BuildSettings.Versions.kodein}")

    // dropwizard metrics
    implementation("io.dropwizard.metrics:metrics-core:${BuildSettings.Versions.dropWizard}")
    implementation("io.dropwizard.metrics:metrics-jmx:${BuildSettings.Versions.dropWizard}")

    // slack
    implementation("com.slack.api:slack-api-client:${BuildSettings.Versions.slack}")
    implementation("com.slack.api:slack-api-model-kotlin-extension:${BuildSettings.Versions.slack}")
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set(BuildSettings.archiveBaseName)
        archiveFileName.set(BuildSettings.archiveFileName)
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to BuildSettings.mainClass))
        }
    }

    build {
        dependsOn(shadowJar)
    }

    test {
        testLogging.showExceptions = true
    }
}
