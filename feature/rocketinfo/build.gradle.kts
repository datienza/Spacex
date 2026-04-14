plugins {
    id("spacex.android.feature")
    id("spacex.android.hilt")
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.datienza.spacex.feature.rocketinfo"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data:launches"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)
    implementation(libs.mpandroidchart)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.livedata.testing)
    testImplementation(libs.rxjava3)
    testImplementation(libs.rxandroid3)
    testImplementation(project(":core:common"))
}
