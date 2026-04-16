interface HiltExtension : BaseExtension {

    fun hilt() = with(project) {
        configureHilt()
    }
}