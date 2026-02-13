import java.util.Locale
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.parcelize")
}

fun loadProperty(propertyName: String, defaultValue: String = ""): String {
    (project.findProperty(propertyName) as String?)?.takeIf { it.isNotBlank() }?.let { return it }

    System.getenv(propertyName)?.takeIf { it.isNotBlank() }?.let { return it }
    System.getenv(propertyName.uppercase(Locale.US))?.takeIf { it.isNotBlank() }?.let { return it }

    val propertiesFile = rootProject.file("local.properties")
    return Properties().apply {
        if (propertiesFile.exists()) load(propertiesFile.inputStream())
    }.getProperty(propertyName, defaultValue)
}

// Load properties using the function
val apiKey: String by lazy { loadProperty("API_KEY", "") }
val baseUrl: String by lazy { loadProperty("BASE_URL", "") }
val baseUrlImage: String by lazy { loadProperty("BASE_URL_IMAGE", "") }

android {
    namespace = "com.mshell.shellcinema"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mshell.shellcinema"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "BASE_URL_IMAGE", "\"$baseUrlImage\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.text.google.fonts)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)

    // Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.ilyapavlovskii.youtubeplayer)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}