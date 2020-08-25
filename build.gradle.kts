buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0")
        classpath("com.android.tools.build:gradle:3.5.2")
    }
}
group = "com.prof18"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
