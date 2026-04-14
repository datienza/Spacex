import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin that wires up the standard baseline test dependencies.
 * Delegates to [configureTesting] — see that file for the full dependency list.
 */
class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureTesting()
        }
    }
}
