import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Wires up the standard baseline test dependencies for any module.
 *
 * Unit tests:        JUnit4, Mockito-Kotlin, Truth, coroutines-test, arch-core-testing
 * Instrumented tests: Espresso, androidx-test runner
 *
 * Module-specific extras (RxJava, LiveData-testing, etc.) should still be
 * declared manually in each module's build.gradle.kts.
 */
internal fun Project.configureTesting() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        // Unit tests
        add("testImplementation", libs.findLibrary("junit").get())
        add("testImplementation", libs.findLibrary("mockito-kotlin").get())
        add("testImplementation", libs.findLibrary("google-truth").get())
        add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
        add("testImplementation", libs.findLibrary("arch-core-testing").get())

        // Instrumented tests
        add("androidTestImplementation", libs.findLibrary("espresso-core").get())
        add("androidTestImplementation", libs.findLibrary("androidx-test-runner").get())
    }
}
