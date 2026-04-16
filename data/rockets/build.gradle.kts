plugins {
    alias(libs.plugins.spacex.android.library)
}

wup {
    hilt()
    serialisation()
}

android {
    namespace = "com.datienza.spacex.data.rockets"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))

    implementation(libs.retrofit.core)
}
