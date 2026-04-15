import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.LintPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.hasPlugin

internal fun Project.configureAndroidLint() {
    when {
        plugins.hasPlugin(AppPlugin::class) ->
            configure<ApplicationExtension> {
                lint(Lint::configure)
            }

        plugins.hasPlugin(LibraryPlugin::class) ->
            configure<LibraryExtension> {
                lint(Lint::configure)
            }

        else -> {
            plugins.apply(LintPlugin::class)
            configure<Lint>(Lint::configure)
        }
    }
}

private fun Lint.configure() {
    xmlReport = false
    checkReleaseBuilds = false

    disable +=
        // Labels are handed externally
        "TypographyDashes" +
        "TypographyEllipsis" +
        "Typos" +
        "ButtonCase" +

        "MParticleInitialization"
}
