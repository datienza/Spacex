import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Wires up the standard Kotlin Coroutines dependency set.
 * - [kotlinx-coroutines-core] unconditionally — works for both JVM and Android modules.
 * - [kotlinx-coroutines-android] only on Android modules via [onAndroid].
 * - [kotlinx-coroutines-test] added to testImplementation.
 */
internal fun Project.configureKotlinCoroutines() {
    dependencies {
        "implementation"(libs.library("kotlinx-coroutines-core"))
        "testImplementation"(libs.library("kotlinx-coroutines-test"))

        onAndroid {
            "implementation"(libs.library("kotlinx-coroutines-android"))
        }
    }
}