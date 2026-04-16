plugins {
    alias(libs.plugins.spacex.jvm.library)
}

dependencies {
    implementation(project(":lib:hilt-binder:annotations"))
    implementation(libs.ksp.api)
}
