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
  ":feature:home",
  ":build_config",
  ":core:calendar",
  ":feature:register",
  ":feature:main",
  ":core:domain",
  ":feature:resource",
  ":common",
  ":feature:search",
  ":feature:detail",
)