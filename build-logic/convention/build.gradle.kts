plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "spacex.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "spacex.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "spacex.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "spacex.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidCompose") {
            id = "spacex.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidTest") {
            id = "spacex.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidCoroutines") {
            id = "spacex.android.coroutines"
            implementationClass = "AndroidCoroutinesConventionPlugin"
        }
    }
}
