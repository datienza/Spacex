plugins {
    id("spacex.android.library")
    id("spacex.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.datienza.spacex.core.network"
}

dependencies {
    api(libs.retrofit.core)
    api(libs.retrofit.converter.kotlinx.serialization)
    api(libs.retrofit.adapter.rxjava3)
    api(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    api(libs.kotlinx.serialization.json)
    implementation(libs.rxjava3)
}
