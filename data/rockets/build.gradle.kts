plugins {
    id("spacex.android.library")
    id("spacex.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.datienza.spacex.data.rockets"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))

    implementation(libs.retrofit.core)
    implementation(libs.rxjava3)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.google.truth)
    testImplementation(libs.rxjava3)
    testImplementation(libs.rxandroid3)
}
