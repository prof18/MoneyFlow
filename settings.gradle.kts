pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencyResolutionManagement {
        repositories {
            mavenCentral()
        }
    }
}
rootProject.name = "money-flow"


include(":shared")
include(":androidApp")
include(":dropbox-api")
