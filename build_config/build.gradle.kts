import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.internal.provider.ManagedFactories.ProviderManagedFactory

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.kotlin.android)
}

android {
  namespace = "jjh.devlieryservice.build_config"
  compileSdk = 34

  defaultConfig {
    buildConfigField("String", "API_KEY", getApiKey("SMART_TRACKER_API_KEY"))
  }

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
}

fun getApiKey(propertyKey: String): String {
  return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}