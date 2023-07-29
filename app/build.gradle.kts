plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "io.github.xtvj.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.xtvj.android"
        minSdk = 26
        targetSdk = 34
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
    lint {
        // if true, stop the gradle build if errors are found
        abortOnError = false
        // set to true to have all release builds run lint on issues with severity=fatal
        // and abort the build (controlled by abortOnError above) if fatal issues are found
        checkReleaseBuilds = true
        // if true, only report errors
        ignoreWarnings = true
        //忽略Every Initializer needs to be accompanied by a corresponding <meta-data> entry in the AndroidManifest.xml file
        disable += mutableSetOf("EnsureInitializerMetadata")
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":network"))

    implementation(libs.bundles.ktxs)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.recyclerview)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)

    implementation(libs.bundles.room)
    kapt(libs.androidx.room.compiler)

    //协程
    implementation(libs.kotlinx.coroutines.android)
    //startup
    implementation(libs.androidx.startup.runtime)
    //swipe refresh layout
    implementation(libs.androidx.swiperefreshlayout)
    //retrofit封装工具
    implementation (libs.sandwich)
    //data store
    implementation (libs.androidx.datastore.preferences)
    //kotlin标准库
    implementation(libs.kotlin.stdlib)
    //custom popup window
    implementation (libs.balloon)
    implementation(libs.bundles.okhttp)
    implementation(libs.retrofit)
    //二维码
    implementation (libs.zxing.lite)

    //权限管理
    implementation (libs.permissionsdispatcher)
    kapt (libs.permissionsdispatcher.processor)
    //Paging 3
    implementation (libs.androidx.paging.runtime.ktx)

    //图表
    implementation (libs.androidChart)
    //弹窗
    implementation (libs.balloon)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
hilt {
    enableAggregatingTask = true
}