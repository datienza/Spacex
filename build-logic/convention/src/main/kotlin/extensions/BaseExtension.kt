package extensions

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

interface BaseExtension: ExtensionAware {
    val project: Project
}