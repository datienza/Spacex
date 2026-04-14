import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin

/**
 * Convention plugin for modules that use Jetpack Compose.
 *
 * Applying `spacex.android.compose` to a library module will:
 *  - Enable the `compose` build feature.
 *  - Apply the Kotlin Compose compiler plugin (`org.jetbrains.kotlin.plugin.compose`).
 *  - Add the Compose BOM and a standard set of Compose dependencies automatically.
 *
 * Usage in a module's build.gradle.kts:
 * ```kotlin
 * plugins {
 *     id("spacex.android.library")   // or spacex.android.feature
 *     id("spacex.android.compose")
 * }
 * ```
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Kotlin Compose compiler plugin (required since Kotlin 2.0)
            apply<ComposeCompilerGradleSubplugin>()

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
            }

            val libs = extensions
                .getByType<org.gradle.api.artifacts.VersionCatalogsExtension>()
                .named("libs")

            dependencies {
                val bom = libs.findLibrary("compose-bom").get()

                // BOM — pins all androidx.compose.* versions
                add("implementation", platform(bom))

                // Core Compose
                add("implementation", libs.findLibrary("compose-ui").get())
                add("implementation", libs.findLibrary("compose-ui-graphics").get())
                add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("compose-material3").get())
                add("implementation", libs.findLibrary("lifecycle-viewmodel-compose").get())
                add("implementation", libs.findLibrary("lifecycle-runtime-compose").get())

                // Debug-only tooling
                add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("compose-ui-test-manifest").get())
            }
        }
    }
}
