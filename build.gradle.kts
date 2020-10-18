buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("com.android.tools.build:gradle:4.2.0-alpha13")
        classpath(Deps.SqlDelight.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
//        maven(url = "https://dl.bintray.com/ekito/koin" )
        maven(url = "https://dl.bintray.com/touchlabpublic/kotlin")
    }
}


group = "com.prof18"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
