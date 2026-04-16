plugins {
    alias(libs.plugins.spacex.android.library)
}

android {
    namespace = "com.datienza.spacex.core.common"
}

dependencies {
    api(project(":core:model"))

    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.livedata.ktx)

    // Coroutines exposed via BaseViewModel
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
}
