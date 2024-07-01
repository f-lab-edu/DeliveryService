plugins {
  kotlin("jvm")
  kotlin("kapt")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

  // Coroutine
  implementation(libs.kotlinx.coroutines.core)
}