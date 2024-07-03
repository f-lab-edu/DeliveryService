plugins {
  kotlin("jvm")
  kotlin("kapt")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

  implementation(libs.kotlin.stdlib)

  testImplementation(libs.junit)
  testImplementation(libs.truth)
  testImplementation(libs.mockk)
}