plugins {
    alias(libs.plugins.spacex.jvm.library)
}

wup {
    hilt()
    serialisation()
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
