import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin

/**
 * Convention plugin for modules that use Jetpack Compose.
 * Applies the Kotlin Compose compiler plugin and delegates to [configureCompose].
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<ComposeCompilerGradleSubplugin>()

            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }
        }
    }
}
