plugins {
    id("spacex.android.application")
    id("spacex.android.hilt")
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.datienza.spacex"

    defaultConfig {
        applicationId = "com.datienza.spacex"
        versionCode   = 1
        versionName   = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":data:rockets"))
    implementation(project(":data:launches"))
    implementation(project(":feature:rockets"))
    implementation(project(":feature:rocketinfo"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.timber)
}
