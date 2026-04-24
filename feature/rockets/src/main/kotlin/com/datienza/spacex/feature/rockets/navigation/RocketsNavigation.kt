package com.datienza.spacex.feature.rockets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.datienza.spacex.feature.rockets.ui.RocketsRoute

const val ROCKETS_ROUTE = "rockets"

fun NavGraphBuilder.rocketsScreen(
    onNavigateToRocketInfo: (rocketId: String, rocketDescription: String) -> Unit,
) {
    composable(ROCKETS_ROUTE) {
        RocketsRoute(onNavigateToRocketInfo = onNavigateToRocketInfo)
    }
}
