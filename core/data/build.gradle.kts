plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.googleDaggerHilt)
  id("kotlin-kapt")
}

android {
  namespace = "jjh.deliveryservice.data"
  compileSdk = 34

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(project(":core:domain"))
  implementation(project(":build_config"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)

  // hilt
  implementation(libs.hilt.android)
  kapt(libs.hilt.android.compiler)

  // Retrofit2
  implementation(libs.retrofit)
  implementation(libs.converter.gson)
  implementation(libs.converter.scalars)

  // Okhttp
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)

  // Room
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  kapt(libs.androidx.room.compiler)

  // Coroutine
  implementation(libs.kotlinx.coroutines.android)

  // Logger
  implementation(libs.logger)


  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}