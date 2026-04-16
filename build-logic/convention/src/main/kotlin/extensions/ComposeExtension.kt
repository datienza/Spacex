interface ComposeExtension : BaseExtension {

    fun composeUi(
        exportDependencies: Boolean = false,
    ) = with(project) {
        configureCompose(exportDependencies)
    }
}