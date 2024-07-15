pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "DeliveryService"
include(
  ":app",

  ":core:data",

  ":feature:home"
)
include(":build_config")
//include(":build_config_stub")
include(":core:calendar")
include(":feature:register")
include(":feature:main")
include(":core:domain")
include(":feature:resource")
include(":common")
