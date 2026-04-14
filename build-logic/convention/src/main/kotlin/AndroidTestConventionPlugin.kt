import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Convention plugin that wires up a standard baseline of test dependencies.
 *
 * Unit tests (testImplementation):
 *  - JUnit 4
 *  - Mockito-Kotlin
 *  - Google Truth
 *  - kotlinx-coroutines-test
 *  - androidx.arch.core:core-testing  (InstantTaskExecutorRule)
 *
 * Instrumented tests (androidTestImplementation):
 *  - Espresso core
 *  - androidx.test runner
 *  - Compose UI test (ui-test-junit4) — no-op when Compose is not used
 *
 * Module-specific extras (RxJava, LiveData-testing, etc.) should still be
 * declared manually in each module's build.gradle.kts.
 *
 * Usage:
 * ```kotlin
 * plugins {
 *     id("spacex.android.library")   // or spacex.android.feature
 *     id("spacex.android.test")
 * }
 * ```
 */
class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // ----- Unit tests -----
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("mockito-kotlin").get())
                add("testImplementation", libs.findLibrary("google-truth").get())
                add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
                add("testImplementation", libs.findLibrary("arch-core-testing").get())

                // ----- Instrumented tests -----
                add("androidTestImplementation", libs.findLibrary("espresso-core").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-runner").get())
            }
        }
    }
}
