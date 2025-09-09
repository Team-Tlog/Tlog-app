import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

    // Nav TypeSafe
//    kotlin("plugin.serialization")
    alias(libs.plugins.kotlin.serialization)
    // firebase
    id("com.google.gms.google-services")
}




android {
    namespace = "com.tlog"
    compileSdk = 35

    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))


    defaultConfig {
        applicationId = "com.tlog"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        //Manifest에서 사용하는 용도
        //카카오 네이티브 앱키

        val kakaoNativeAppKey = localProperties.getProperty("KAKAO_NATIVE_APP_KEY")
        manifestPlaceholders["KakaoScheme"] = "kakao${kakaoNativeAppKey}"
        manifestPlaceholders["KakaoNativeAppKey"] = kakaoNativeAppKey

        //실제 코드에서 사용하는 용도
        //구글 클라이언트 ID
        val googleClientId =  localProperties.getProperty("GOOGLE_CLIENT_ID")
        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$googleClientId\"")

        //카카오 네이티브 앱키
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${kakaoNativeAppKey}\"")

        //네이버 클라이언트 ID, 클라이언트 시크릿
        val naverClientId = localProperties.getProperty("NAVER_CLIENT_ID")
        val naverClientSecret = localProperties.getProperty("NAVER_CLIENT_SECRET")
        buildConfigField("String", "NAVER_CLIENT_ID", "\"$naverClientId\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"$naverClientSecret\"")
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
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Tlog
    // Jetpack Compose
    implementation("androidx.activity:activity-compose:1.9.0")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    debugImplementation("androidx.compose.ui:ui-tooling:1.5.3")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // 애니매이션 네비
    // implementation("com.google.accompanist:accompanist-navigation-animation:0.32.0")
    // implementation("androidx.navigation:navigation-compose:2.7.7")

    // Material3
//    implementation("androidx.compose.material3:material3")

    // 접근 권한 (갤러리)
    implementation(libs.accompanist.permissions)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter (JSON 파싱용)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (네트워크 통신, Retrofit 내부에서 사용)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // OkHttp Logging Interceptor (통신 로그 찍고 싶을 때 사용)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Preferences DataStore (키-값 저장 시 사용)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // 코루틴 사용
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //카카오 로그인용 sdk
    implementation("com.kakao.sdk:v2-all:2.21.1")

    //구글 로그인
    implementation ("com.google.android.gms:play-services-auth:21.0.0")

    //네이버 로그인
    implementation ("com.navercorp.nid:oauth:5.10.0")


    // hilt
    implementation("com.google.dagger:hilt-android:2.56.1")
    ksp("com.google.dagger:hilt-android-compiler:2.56.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Nav
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // firebase messaging service
    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-messaging:23.0.3")
    implementation("com.google.firebase:firebase-messaging-ktx")
    // await (콜백 -> 비동기)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // 구글 로그인 (파이어베이스)
//    apply(plugin = "com.google.gms.google-services")

    // Kakao map
    implementation("com.kakao.maps.open:android:2.12.8")

    // GPS
    implementation("com.google.android.gms:play-services-location:21.3.0")
}
