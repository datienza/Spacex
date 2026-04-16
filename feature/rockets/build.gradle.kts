plugins {
    alias(libs.plugins.spacex.android.library)
}

wup {
    hilt()
}

android {
    namespace = "com.datienza.spacex.feature.rockets"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data:rockets"))

    testImplementation(project(":core:common"))
}
