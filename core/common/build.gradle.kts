plugins {
    id("spacex.android.library")
}

android {
    namespace = "com.datienza.spacex.core.common"
}

dependencies {
    api(project(":core:model"))

    // Lifecycle (ViewModel base class + viewModelScope)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.livedata.ktx)

    // Coroutines (BaseViewModel)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    // RxJava (DisposingViewModel — kept for legacy support)
    api(libs.rxjava3)
    api(libs.rxandroid3)
    api(libs.rxkotlin3)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.rxjava3)
    testImplementation(libs.rxandroid3)
}
