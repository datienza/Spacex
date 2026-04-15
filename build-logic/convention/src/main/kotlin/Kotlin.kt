import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


internal fun Project.configureKotlin() =
    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(AndroidVersions.Jvm)
    }