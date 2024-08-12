import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.googleDaggerHilt)
  id("kotlin-kapt")
}

android {
  namespace = "jjh.deliveryservice.home"
  compileSdk = 34

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildFeatures {
    compose = true
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {

  // domain의 level class를 위함. 모델이라는 모듈을 따로 만들어서 빼는것도 고려 필요할듯 싶음.
  implementation(project(":core:domain"))
  implementation(project(":feature:resource"))

  // hilt
  implementation(libs.hilt.android)
  kapt(libs.hilt.android.compiler)
  implementation(libs.androidx.hilt.navigation.compose)

  // gson
  implementation(libs.converter.gson)

}