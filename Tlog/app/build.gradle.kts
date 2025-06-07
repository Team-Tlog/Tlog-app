plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

    // firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.tlog"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tlog"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //Manifest에서 사용하는 용도
        //카카오 네이티브 앱키
        manifestPlaceholders["KakaoScheme"] = "kakao${project.findProperty("KAKAO_NATIVE_APP_KEY")}"

        //실제 코드에서 사용하는 용도
        //구글 클라이언트 ID
        buildConfigField("String","GOOGLE_CLIENT_ID", "\"${property("GOOGLE_CLIENT_ID")}\"")
        //카카오 네이티브 앱키
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${project.findProperty("KAKAO_NATIVE_APP_KEY")}\"")
        //네이버 클라이언트 ID, 클라이언트 시크릿
        buildConfigField("String", "NAVER_CLIENT_ID", "\"${project.findProperty("NAVER_CLIENT_ID")}\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"${project.findProperty("NAVER_CLIENT_SECRET")}\"")
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
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.3")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Material3
    implementation("androidx.compose.material3:material3:1.1.2")

    // Navigation (화면 전환)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 접근 권한 (갤러리)
    implementation(libs.accompanist.permissions)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter (JSON 파싱용)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (네트워크 통신, Retrofit 내부에서 사용)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

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
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // await (콜백 -> 비동기)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // 구글 로그인 (파이어베이스)
    apply(plugin = "com.google.gms.google-services")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Kakao map
    implementation("com.kakao.maps.open:android:2.12.8")
}
