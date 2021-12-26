buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.kotlinGradle)
        classpath(Deps.agp)
        classpath(Deps.SqlDelight.gradle)
        classpath(Deps.gradleVersion)
    }
}

plugins {
    id(Deps.gradleVersionPlugin) version Versions.gradleVersions
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
}


group = Config.Release.sharedLibGroup
version = Config.Release.sharedLibVersion

repositories {
    mavenCentral()
}
