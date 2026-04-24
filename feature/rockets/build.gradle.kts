plugins {
    alias(libs.plugins.spacex.android.library)
}

wup {
    hilt()
    composeUi()
}

android {
    namespace = "com.datienza.spacex.feature.rockets"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data:rockets"))

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    testImplementation(project(":core:common"))
}
