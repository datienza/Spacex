import com.google.devtools.ksp.gradle.KspGradleSubplugin
import dagger.hilt.android.plugin.HiltGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Convention plugin that wires up Hilt + KSP for any module.
 * Applies: KspGradleSubplugin, HiltGradlePlugin
 * Adds:   hilt-android (implementation), hilt-compiler (ksp)
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<KspGradleSubplugin>()
            apply<HiltGradlePlugin>()

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", libs.findLibrary("hilt-android").get())
                add("ksp",            libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}
