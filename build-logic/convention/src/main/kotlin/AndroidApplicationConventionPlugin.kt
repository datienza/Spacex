import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<AppPlugin>()
            apply<KotlinAndroidPluginWrapper>()

            configureAndroidLint()

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)
                defaultConfig.targetSdk = AndroidVersions.TargetSdk
            }

            // Java + Kotlin toolchain
            configureJava()
            configureKotlinCoroutines()
            configureTesting()

            extensions.create("wup", AndroidApplicationExtension::class)
        }
    }
}
