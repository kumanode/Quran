import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.quran.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.quran.app"
        minSdk = 24
        targetSdk = 35

        // I don't know why I've used such a weird versioning scheme in the beginning,
        // but I can't change it now as the app is already in the Play Store
        // now just incrementing from there
        versionCode = 23_11_11_142
        versionName = "1.0.0"

        resValue("string", "app_name", "Quran")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.generatedDensities()

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas", "room.incremental" to "true"
                )
            }
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
        buildConfig = true
        resValues = true
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"

            resValue("string", "app_name", "Quran Debug")

            /* ---------------------------------------------------------------- */
            resValue("string", "cleartextTrafficPermitted", "true")
        }

        release {
            signingConfig = signingConfigs.getByName("debug") // Will be overridden if properties exist
            
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))
                
                signingConfigs.create("release") {
                    storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
                    storePassword = keystoreProperties.getProperty("storePassword")
                    keyAlias = keystoreProperties.getProperty("keyAlias")
                    keyPassword = keystoreProperties.getProperty("keyPassword")
                }
                signingConfig = signingConfigs.getByName("release")
            }

            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            /* ---------------------------------------------------------------- */
            resValue("string", "cleartextTrafficPermitted", "false")
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependenciesInfo {
        // Disables dependency metadata when building APKs (for IzzyOnDroid/F-Droid)
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles (for Google Play)
        includeInBundle = false
    }
}

base {
    archivesName = android.defaultConfig.versionName
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(":peacedesign"))

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowSizeClass)
    implementation(libs.compose.material3.adaptive)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.ui.tooling)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.androidx.coreKtx)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.service)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.legacySupport)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.media)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.activityKtx)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.fragmentKtx)

    implementation(libs.media3ExoPlayer)
    implementation(libs.media3Datasource)
    implementation(libs.media3Database)
    implementation(libs.media3Session)
    implementation(libs.media3UI)

    coreLibraryDesugaring(libs.desugaring)
    implementation(libs.material)
    implementation(libs.apache.commons)
    implementation(libs.guava)
    implementation(libs.viewbinding)

    /* kotlinx serialization */
    implementation(libs.retrofit)
    implementation(libs.kotlinxSerialization)
    implementation(libs.kotlinxRetrofit)

    implementation(libs.commonsCompress)
    implementation(libs.workManager)
    implementation(libs.dataStore)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.paging)
    implementation(libs.pagingCompose)
    implementation(libs.roomPaging)


    implementation(libs.compose.navigation)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.accompanist.permissions)
    implementation(libs.glance)
    implementation(libs.glance.appwidget)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    
    // Solana Mobile Wallet Adapter
    implementation("com.solanamobile:mobile-wallet-adapter-clientlib-ktx:2.1.0")
    // Sol4k for building transactions and RPC calls
    implementation("org.sol4k:sol4k:0.7.0")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
