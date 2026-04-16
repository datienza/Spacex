plugins {
    alias(libs.plugins.spacex.android.library)
    alias(libs.plugins.navigation.safeargs)
}

wup {
    hilt()
}

android {
    namespace = "com.datienza.spacex.feature.rocketinfo"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data:launches"))

    testImplementation(project(":core:common"))
}
