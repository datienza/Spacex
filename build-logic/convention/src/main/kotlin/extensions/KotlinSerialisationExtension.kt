package extensions

import configureSerialisation

interface KotlinSerialisationExtension : BaseExtension {

    fun serialisation(
        exportDependencies: Boolean = false,
    ) = with(project) {
        configureSerialisation(exportDependencies)
    }
}