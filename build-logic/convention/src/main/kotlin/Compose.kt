import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Enables the Compose build feature and wires up the standard Compose dependency set
 * (BOM + UI + Material3 + Lifecycle-Compose + debug tooling).
 */
internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.buildFeatures {
        compose = true
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        val bom = libs.findLibrary("compose-bom").get()

        add("implementation", platform(bom))
        add("implementation", libs.findLibrary("compose-ui").get())
        add("implementation", libs.findLibrary("compose-ui-graphics").get())
        add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("compose-material3").get())
        add("implementation", libs.findLibrary("lifecycle-viewmodel-compose").get())
        add("implementation", libs.findLibrary("lifecycle-runtime-compose").get())

        add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
        add("debugImplementation", libs.findLibrary("compose-ui-test-manifest").get())
    }
}
