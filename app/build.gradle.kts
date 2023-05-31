plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
//    id(libs.plugins.devtools.ksp.get().pluginId)
}

android {
    namespace = "io.github.xtvj.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.xtvj.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)
    implementation (libs.androidx.constraintlayout)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)

    implementation (libs.androidx.fragment.ktx)
    implementation (libs.androidx.activity.ktx)
    implementation (libs.androidx.recyclerview)

    implementation (libs.androidx.recyclerview.selection)

    //协程
    implementation (libs.kotlinx.coroutines.android)

    //依赖注入
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.common.java8)
    implementation (libs.androidx.lifecycle.viewmodel.savedstate)
    // optional - Test helpers for LiveData
    testImplementation (libs.androidx.core.testing)
    // optional - Test helpers for Lifecycle runtime
    testImplementation (libs.androidx.lifecycle.runtime.testing)

    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.room.paging)
    kapt (libs.androidx.room.compiler)
    testImplementation (libs.androidx.room.testing)

    //Paging 3
    implementation (libs.androidx.paging.runtime.ktx)

    //startup
    implementation (libs.androidx.startup.runtime)

    //log
    implementation (libs.timber)

    //net
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.moshi.kotlin)

    //retrofit封装工具
    implementation (libs.sandwich)

    //data store
    implementation (libs.androidx.datastore.preferences)

    //swipe refresh layout
    implementation (libs.androidx.swiperefreshlayout)

    // Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    androidTestImplementation (libs.androidx.navigation.testing)
    // Feature module Support
    implementation (libs.androidx.navigation.dynamic.features.fragment)

    //flexbox
    implementation (libs.flexbox)

    //coil图片加载
    implementation (libs.bundles.coil)

    //图表
    implementation (libs.androidChart)

    //内存检测
    debugImplementation (libs.leakcanary.android)

    //权限管理
    implementation (libs.permissionsdispatcher)
    kapt (libs.permissionsdispatcher.processor)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}