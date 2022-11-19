pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencyResolutionManagement {
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
