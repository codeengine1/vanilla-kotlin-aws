object Version {
    const val dropWizard = "4.2.3"
    const val kotlin = "1.5.21"
    const val kotlinCoroutines = "1.5.1"
    const val kotlinImmutable = "0.3.4"
    const val ktor = "1.6.2"
    const val slack = "1.3.2"
}

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.kotlinCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:${Version.kotlinImmutable}")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${Version.kotlin}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}")

    // ktor
    implementation("io.ktor:ktor:${Version.ktor}")
    implementation("io.ktor:ktor-jackson:${Version.ktor}")
    implementation("io.ktor:ktor-server-netty:${Version.ktor}")
    implementation("io.ktor:ktor-auth:${Version.ktor}")
    implementation("io.ktor:ktor-auth-jwt:${Version.ktor}")
    implementation("io.ktor:ktor-metrics:${Version.ktor}")
    implementation("io.ktor:ktor-html-builder:${Version.ktor}")
    implementation("io.ktor:ktor-server-test-host:${Version.ktor}")
    implementation("io.ktor:ktor-server-sessions:${Version.ktor}")

    // dropwizard metrics
    implementation("io.dropwizard.metrics:metrics-core:${Version.dropWizard}")
    implementation("io.dropwizard.metrics:metrics-jmx:${Version.dropWizard}")

    // slack
    implementation("com.slack.api:slack-api-client:${Version.slack}")
    implementation("com.slack.api:slack-api-model-kotlin-extension:${Version.slack}")
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
    test {
        testLogging.showExceptions = true
    }
}
