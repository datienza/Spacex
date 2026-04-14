plugins {
    id("spacex.android.feature")
    id("spacex.android.hilt")
}

android {
    namespace = "com.datienza.spacex.feature.rockets"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data:rockets"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.glide)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.livedata.testing)
    testImplementation(libs.rxjava3)
    testImplementation(libs.rxandroid3)
    testImplementation(project(":core:common"))
}
