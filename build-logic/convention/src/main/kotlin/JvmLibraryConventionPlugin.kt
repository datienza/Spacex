import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

class JvmLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<KotlinPluginWrapper>()

            // Java + Kotlin toolchain
            configureJava()
            configureKotlinCoroutines()
            configureTesting()

            extensions.create("wup", JvmLibraryExtension::class)
        }
    }
}