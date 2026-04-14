import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin for feature modules.
 * Applies [AndroidLibraryConventionPlugin] + enables viewBinding.
 * Automatically applies [AndroidTestConventionPlugin] so every feature module
 * gets the standard test dependencies without extra boilerplate.
 * Other dependencies (lifecycle, hilt, etc.) are declared per feature module.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<AndroidLibraryConventionPlugin>()
            apply<AndroidTestConventionPlugin>()

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    viewBinding = true
                }
            }
        }
    }
}
