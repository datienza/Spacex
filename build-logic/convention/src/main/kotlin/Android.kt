import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BasePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Shared Android configuration applied to both application and library modules.
 * Receives [CommonExtension] as a parameter — the modern stable API used by NiA.
 * [KotlinAndroidProjectExtension.jvmToolchain] sets both Java source/target compatibility
 * and Kotlin jvmTarget in one place, so no separate compileOptions block is needed.
 */
internal fun Project.configureAndroid(
    extension: CommonExtension<*, *, *, *, *, *>,
) {
    extension.apply {
        compileSdk = AndroidVersions.CompileSdk

        defaultConfig {
            minSdk = AndroidVersions.MinSdk
        }
    }
}

internal fun Project.onAndroid(block: Project.() -> Unit) {
    plugins.withType<BasePlugin> {
        block()
    }
}
