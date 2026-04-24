import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Applies the Compose compiler plugin, enables the build feature, and wires up dependencies.
 *
 * @param exportDependencies  if true, uses `api` so Compose types are visible to module consumers.
 */
internal fun Project.configureCompose(
    exportDependencies: Boolean = false,
) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    dependencies {
        configure(exportDependencies)(platform(libs.library("compose-bom")))

        composeLibraries().forEach { alias ->
            configure(exportDependencies)(libs.library(alias))
        }

        configure(exportDependencies)(libs.library("compose-ui-tooling-preview"))
        configureDebug(exportDependencies)(libs.library("compose-ui-tooling"))

        onAndroid {
            "debugImplementation"(libs.library("compose-ui-test-manifest"))
        }
    }
}

/**
 * Returns catalog aliases for the standard Compose dependency set.
 * [useMaterial] controls whether [compose-material3] is included.
 */
private fun composeLibraries() = buildList {
    add("compose-ui")
    add("compose-ui-graphics")
    add("compose-foundation")
    add("lifecycle-viewmodel-compose")
    add("lifecycle-runtime-compose")
    add("activity-compose")
    add("compose-material3")
    add("compose-material-icons")
}

/** `"api"` when [export] is true, `"implementation"` otherwise. */
private fun configure(export: Boolean) = if (export) "api" else "implementation"

/** `"debugApi"` when [export] is true, `"debugImplementation"` otherwise. */
private fun configureDebug(export: Boolean) = if (export) "debugApi" else "debugImplementation"
