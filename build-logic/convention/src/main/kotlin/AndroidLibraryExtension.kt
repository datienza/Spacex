import org.gradle.api.Project

abstract class AndroidLibraryExtension(
    override val project: Project,
) : HiltExtension, ComposeExtension, KotlinSerialisationExtension