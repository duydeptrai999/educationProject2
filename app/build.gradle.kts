plugins {
    id("com.android.application")
    id("jacoco")
}

android {
    namespace = "com.duyth10.dellhieukieugi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.duyth10.dellhieukieugi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {

        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true // Bao gồm tài nguyên Android cho Unit Tests
     //   unitTests.isReturnDefaultValues = true
     //   execution = "ANDROIDX_TEST_ORCHESTRATOR"

    }
}

jacoco {
    toolVersion = "0.8.10"
}

// Task tạo báo cáo coverage cho Unit Tests
tasks.register<JacocoReport>("jacocoUnitTestReport") {
    group = "verification"
    description = "Generates Jacoco coverage reports for unit tests."

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtmlUnitTest"))
    }

    val fileFilter = listOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*",
        "**/Manifest*.*", "**/*Test*.*", "android/**/*.*"
    )

    val classDirs = fileTree(
        mapOf(
            "dir" to "${buildDir}/tmp/kotlin-classes/debug",
            "excludes" to fileFilter
        )
    )

    val sourceDirs = files("src/main/java")

    classDirectories.setFrom(classDirs)
    sourceDirectories.setFrom(sourceDirs)

    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to "${buildDir}/jacoco",
                "includes" to listOf("testDebugUnitTest.exec")
            )
        )
    )
}

// Task tạo báo cáo coverage cho Instrumentation Tests
tasks.register("createDebugCoverageReport", JacocoReport::class) {
    dependsOn("connectedDebugAndroidTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    val fileFilter = listOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*",
        "**/Manifest*.*", "**/*Test*.*", "android/**/*.*"
    )

    val classDirs = fileTree(
        mapOf(
            "dir" to "${buildDir}/intermediates/javac/debug", // Hoặc "${buildDir}/tmp/kotlin-classes/debug"
            "excludes" to fileFilter
        )
    )

    val sourceDirs = files("src/main/java")

    classDirectories.setFrom(classDirs)
    sourceDirectories.setFrom(sourceDirs)

    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to "${buildDir}/outputs/code_coverage/debugAndroidTest/connected",
                "includes" to listOf("**/*.ec")
            )
        )
    )
}

dependencies {
    // Core libraries
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Unit Test dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("androidx.test.ext:junit:1.1.2")
    testImplementation("androidx.test:rules:1.6.1")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Android Test dependencies
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")

    // Fragment Testing cho Debug builds
    debugImplementation("androidx.fragment:fragment-testing:1.8.3")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.0")
}

tasks.named("jacocoUnitTestReport") {
    dependsOn(tasks.named("testDebugUnitTest"))
}