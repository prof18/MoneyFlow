plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    compileOnly(libs.detekt.gradle)
    compileOnly(libs.detekt.formatting)
}

gradlePlugin {
    plugins {
        register("detekt") {
            id = "com.moneyflow.detekt"
            implementationClass = "DetektConventionPlugin"
        }
    }
}
