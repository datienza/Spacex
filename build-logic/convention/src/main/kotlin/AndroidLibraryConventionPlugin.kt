import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<LibraryPlugin>()
            apply<KotlinAndroidPluginWrapper>()

            configureAndroidLint()

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
                buildFeatures {
                    buildConfig = false
                }
            }

            // Java + Kotlin toolchain
            configureJava()
            configureKotlinCoroutines()
            configureTesting()

            extensions.create("wup", AndroidLibraryExtension::class)
        }
    }
}
