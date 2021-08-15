object Version {
    const val kotlin = "1.5.21"
    const val kotlinCoroutines = "1.5.1"
    const val slack = "1.3.2"
    const val dropWizard = "4.2.3"
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
    testImplementation("org.jetbrains.kotlin:kotlin-test:${Version.kotlin}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}")

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
