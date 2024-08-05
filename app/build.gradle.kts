import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.googleDaggerHilt)
  id("kotlin-kapt")
}


android {
  signingConfigs {
    create("release") {
      storePassword = getLocalProperty("KEYSTORE_STORE_PASSWORD")
      keyAlias = getLocalProperty("KEYSTORE_ALIAS")
      keyPassword = getLocalProperty("KEYSTORE_PASSWORD")
      storeFile = file("${rootDir}/app/keystore/delivery_service_keystore")
    }
  }

  namespace = "jjh.deliveryservice"
  compileSdk = 34

  defaultConfig {
    applicationId = "jjh.deliveryservice"
    minSdk = 24
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")

    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(project(":feature:main"))
  implementation(project(":build_config"))
  implementation(project(":core:data"))
  implementation(project(":core:domain"))
  implementation(libs.logger)

  // hilt
  implementation(libs.hilt.android)
  kapt(libs.hilt.android.compiler)
}


fun getLocalProperty(propertyKey: String): String {
  return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}