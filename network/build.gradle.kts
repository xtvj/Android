plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "io.github.xtvj.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.bundles.ktxs)
    implementation(libs.constraintlayout)
    implementation(libs.bundles.recyclerview)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.okhttp)
    implementation(libs.retrofit)

    //retrofit封装工具
    implementation (libs.sandwich)
    //data store
    implementation (libs.androidx.datastore.preferences)
    //kotlin标准库
    implementation(libs.kotlin.stdlib)
    //协程
    implementation(libs.kotlinx.coroutines.android)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}