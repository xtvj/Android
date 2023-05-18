plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'com.google.dagger.hilt.android'
    id 'kotlin-parcelize'
    id 'androidx.navigation.safeargs.kotlin'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'io.github.xtvj.android'
    compileSdk 33

    defaultConfig {
        applicationId "io.github.xtvj.android"
        minSdk 26
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = '17'
    }
    buildFeatures {
        dataBinding true
        viewBinding true
    }
}

dependencies {

    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    implementation 'androidx.fragment:fragment-ktx:1.5.7'
    implementation 'androidx.activity:activity-ktx:1.7.1'
    implementation 'androidx.recyclerview:recyclerview:1.3.0'

    implementation "androidx.recyclerview:recyclerview-selection:1.1.0"

    //协程
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0'

    //依赖注入
    def hilt = '2.46.1'
    implementation "com.google.dagger:hilt-android:$hilt"
    kapt "com.google.dagger:hilt-compiler:$hilt"


    def lifecycle_version = "2.6.1"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
    // optional - Test helpers for LiveData
    testImplementation "androidx.arch.core:core-testing:2.2.0"
    // optional - Test helpers for Lifecycle runtime
    testImplementation "androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
    // optional - helpers for implementing LifecycleOwner in a Service
//    implementation "androidx.lifecycle:lifecycle-service:$lifecycle_version"
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
//    implementation "androidx.lifecycle:lifecycle-process:$lifecycle_version"

    def room_version = "2.5.1"
    implementation "androidx.room:room-ktx:$room_version"
    implementation "androidx.room:room-runtime:$room_version"
    implementation "androidx.room:room-paging:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"

    //Paging 3
    implementation "androidx.paging:paging-runtime-ktx:3.1.1"

    //startup
    implementation 'androidx.startup:startup-runtime:1.1.1'

    //log
    implementation 'com.jakewharton.timber:timber:5.0.1'

    //net
    implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.11'
    implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-moshi:2.9.0'
    implementation 'com.squareup.moshi:moshi-kotlin:1.14.0'
//    implementation "com.squareup.okhttp3:okhttp-sse:4.10.0"

    //retrofit封装工具
    implementation 'com.github.skydoves:sandwich:1.3.6'

    //data store
    implementation "androidx.datastore:datastore-preferences:1.0.0"

    //swipe refresh layout
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'

    // Navigation
    def nav_version = '2.5.3'
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"
    // Feature module Support
    implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"

    //权限管理
    def permissions = '4.9.2'
    implementation "com.github.permissions-dispatcher:permissionsdispatcher:$permissions"
    kapt "com.github.permissions-dispatcher:permissionsdispatcher-processor:$permissions"

    //flexbox
    implementation 'com.google.android.flexbox:flexbox:3.0.0'

    //coil图片加载
    def coil = '2.3.0'
    implementation "io.coil-kt:coil:$coil"
    implementation "io.coil-kt:coil-gif:$coil"
    implementation "io.coil-kt:coil-svg:$coil"
    implementation "io.coil-kt:coil-video:$coil"

    //图表
    implementation "com.github.AppDevNext:AndroidChart:3.1.0.15"

    //内存检测
    debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.10'
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}