package com.datienza.spacex.presentation.model

import com.datienza.spacex.domain.model.Launch

const val TYPE_LAUNCH_ITEM_HEADER = 0
const val TYPE_LAUNCH_ITEM_DATA = 1

sealed class LaunchItemContent(val type: Int) {
    data class Header(
        val launchYear: Int
    ) : LaunchItemContent(TYPE_LAUNCH_ITEM_HEADER)

    data class Item(
        val launch: Launch
    ) : LaunchItemContent(TYPE_LAUNCH_ITEM_DATA)
}