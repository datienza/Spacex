plugins {
    alias(libs.plugins.spacex.android.library)
}

wup {
    hilt()
    serialisation()
}

android {
    namespace = "com.datienza.spacex.core.network"
}

dependencies {
    api(libs.retrofit.core)
    api(libs.retrofit.converter.kotlinx.serialization)
    api(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // EitherNet — typed API result modeling
    api(libs.eithernet)
    implementation(libs.eithernet.integration.retrofit)
}
