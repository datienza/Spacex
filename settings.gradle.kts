pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Spacex"

include(":app")
include(":feature:rockets")
include(":feature:rocketinfo")
include(":data:rockets")
include(":data:launches")
include(":core:network")
include(":core:model")
include(":core:common")
include(":lib:hilt-binder:annotations")
include(":lib:hilt-binder:compiler")
