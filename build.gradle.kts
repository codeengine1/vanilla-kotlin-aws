import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    java
    `kotlin-dsl`
    distribution
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

object Settings {
    const val mainClass = "io.ktor.server.netty.EngineMain"
    const val archiveBaseName = "application"
    const val archiveFileName = "application.jar"

    object Versions {
        const val amazonCorrettoCryptoProvider = "1.6.1"
        const val dropWizard = "4.2.3"
        const val exposed = "0.33.1"
        const val hikariCP = "5.0.0"
        const val kodein = "7.7.0"
        const val konform = "0.3.0"
        const val kotlin = "1.4.31"
        const val kotlinCoroutines = "1.5.1"
        const val kotlinImmutable = "0.3.4"
        const val ktor = "1.6.2"
        const val mysqlConnector = "8.0.26"
        const val slack = "1.3.2"
        const val springSecurity = "5.5.1"
    }
}

application {
    mainClass.set(Settings.mainClass)
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Settings.Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Settings.Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Settings.Versions.kotlinCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:${Settings.Versions.kotlinImmutable}")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${Settings.Versions.kotlin}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Settings.Versions.kotlin}")

    // ktor
    implementation("io.ktor:ktor:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-jackson:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-auth:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-auth-jwt:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-metrics:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-html-builder:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-server-test-host:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-server-sessions:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-client-core:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-client-cio:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-client-serialization:${Settings.Versions.ktor}")
    implementation("io.ktor:ktor-client-jackson:${Settings.Versions.ktor}")

    // kodein
    implementation("org.kodein.di:kodein-di:${Settings.Versions.kodein}")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:${Settings.Versions.kodein}")
    implementation("org.kodein.di:kodein-di-conf-jvm:${Settings.Versions.kodein}")

    // hikari cp
    implementation("com.zaxxer:HikariCP:${Settings.Versions.hikariCP}")

    // mysql driver
    implementation("mysql:mysql-connector-java:${Settings.Versions.mysqlConnector}")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:${Settings.Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${Settings.Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-java-time:${Settings.Versions.exposed}")

    // dropwizard metrics
    implementation("io.dropwizard.metrics:metrics-core:${Settings.Versions.dropWizard}")
    implementation("io.dropwizard.metrics:metrics-jmx:${Settings.Versions.dropWizard}")

    // slack
    implementation("com.slack.api:slack-api-client:${Settings.Versions.slack}")
    implementation("com.slack.api:slack-api-model-kotlin-extension:${Settings.Versions.slack}")

    // konform validation
    implementation("io.konform:konform-jvm:${Settings.Versions.konform}")

    // coretto crypto
    implementation("software.amazon.cryptools:AmazonCorrettoCryptoProvider:${Settings.Versions.amazonCorrettoCryptoProvider}")

    // spring security (for encryption)
    implementation("org.springframework.security:spring-security-crypto:${Settings.Versions.springSecurity}")
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

kotlin {
    version = Settings.Versions.kotlin
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set(Settings.archiveBaseName)
        archiveFileName.set(Settings.archiveFileName)
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to Settings.mainClass))
        }
    }

    build {
        dependsOn(shadowJar)
    }

    test {
        testLogging.showExceptions = true
    }
}
