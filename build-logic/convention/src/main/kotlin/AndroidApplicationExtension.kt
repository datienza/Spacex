import org.gradle.api.Project

abstract class AndroidApplicationExtension(
    override val project: Project,
) : HiltExtension, ComposeExtension, KotlinSerialisationExtension