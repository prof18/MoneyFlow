pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            mavenLocal()
        }
    }
}
rootProject.name = "money-flow"
include(":shared")
include(":androidApp")
