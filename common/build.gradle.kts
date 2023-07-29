plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "io.github.xtvj.common"
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

    //log
    implementation (libs.timber)
    //Paging 3
    implementation (libs.androidx.paging.runtime.ktx)
    //coil
    implementation(libs.bundles.coil)

    implementation(libs.bundles.ktxs)
    implementation(libs.constraintlayout)
    implementation(libs.bundles.recyclerview)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //协程
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.bundles.okhttp)
    implementation(libs.retrofit)

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}