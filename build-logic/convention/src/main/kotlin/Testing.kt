import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * Wires up the standard baseline test dependencies for any module.
 */
internal fun Project.configureTesting() {
    dependencies {
        "testImplementation"(kotlin("test-junit"))
        "testImplementation"(libs.library("junit"))
        "testImplementation"(libs.library("mockito-kotlin"))
        "testImplementation"(libs.library("google-truth"))
        "testImplementation"(libs.library("kotlinx-coroutines-test"))
        "testImplementation"(libs.library("turbine"))

        onAndroid {
            "androidTestImplementation"(kotlin("test-junit"))
            "androidTestImplementation"(libs.library("kotlinx-coroutines-test"))
            "androidTestImplementation"(libs.library("turbine"))
        }
    }
}