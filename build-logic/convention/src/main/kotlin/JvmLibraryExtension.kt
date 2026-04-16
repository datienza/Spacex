import org.gradle.api.Project

abstract class JvmLibraryExtension(
    override val project: Project,
) : HiltExtension, KotlinSerialisationExtension