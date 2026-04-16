plugins {
    alias(libs.plugins.spacex.android.application)
}

wup {
    hilt()
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
}
