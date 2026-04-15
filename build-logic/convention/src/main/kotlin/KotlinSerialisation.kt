import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/**
 * Wires up the standard Kotlin Coroutines dependency set.
 * - [kotlinx-coroutines-core] unconditionally — works for both JVM and Android modules.
 * - [kotlinx-coroutines-android] only on Android modules via [onAndroid].
 * - [kotlinx-coroutines-test] added to testImplementation.
 */
internal fun Project.configureSerialisation(exportDependencies: Boolean = false,
) {
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    dependencies {
        val configuration = if (exportDependencies) "api" else "implementation"

        configuration(libs.library("kotlinx.serialization.json"))
    }
}