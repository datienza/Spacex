import com.google.devtools.ksp.gradle.KspExtension
import com.google.devtools.ksp.gradle.KspGradleSubplugin
import dagger.hilt.android.plugin.HiltGradlePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureHilt() {
    with(pluginManager) {
        apply<KspGradleSubplugin>()

        onAndroid {
            apply<HiltGradlePlugin>()
        }
    }

    configure<KspExtension> {
        // https://dagger.dev/dev-guide/compiler-options#useBindingGraphFix
        arg("dagger.useBindingGraphFix", "enabled")
    }

    dependencies {
        "implementation"(libs.library("hilt-android"))
        "implementation"(project(":lib:hilt-binder:annotations"))
        "ksp"(libs.library("hilt-compiler"))
        "ksp"(project(":lib:hilt-binder:compiler"))
        "kspTest"(libs.library("hilt-compiler"))

        onAndroid {
            "implementation"(libs.library("hilt-navigation-fragment"))
            "testImplementation"(libs.library("hilt-android-testing"))
            "androidTestImplementation"(libs.library("hilt-android-testing"))
            "kspAndroidTest"(libs.library("hilt-compiler"))
        }
    }
}
