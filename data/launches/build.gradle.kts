plugins {
    alias(libs.plugins.spacex.android.library)
}

wup {
    hilt()
    serialisation()
}

android {
    namespace = "com.datienza.spacex.data.launches"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))
}
