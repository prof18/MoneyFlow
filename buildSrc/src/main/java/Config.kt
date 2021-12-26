import org.gradle.api.JavaVersion

object Config {

    object Android {
        const val compileSdk = 31
        const val minSdk = 24
        const val targetSdk = 31
    }

    object Release {
        const val applicationID = "com.prof18.moneyflow"
        const val appVersionCode = 1
        const val appVersionName = "1.0"
        const val sharedLibVersion = "1.0-SNAPSHOT"
        const val sharedLibGroup = "com.prof18"
    }

    object Java {
        val javaVersion = JavaVersion.VERSION_11
        const val javaVersionNumber = "11"
    }
}