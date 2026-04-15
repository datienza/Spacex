package extensions

import configureHilt

interface HiltExtension: BaseExtension {

    fun hilt() = with(project) {
        configureHilt()
    }
}