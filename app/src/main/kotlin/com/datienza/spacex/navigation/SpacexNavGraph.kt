 package com.datienza.spacex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.datienza.spacex.feature.rocketinfo.navigation.navigateToRocketInfo
import com.datienza.spacex.feature.rocketinfo.navigation.rocketInfoScreen
import com.datienza.spacex.feature.rockets.navigation.ROCKETS_ROUTE
import com.datienza.spacex.feature.rockets.navigation.rocketsScreen

@Composable
fun SpacexNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = ROCKETS_ROUTE) {
        rocketsScreen(
            onNavigateToRocketInfo = { rocketId, description ->
                navController.navigateToRocketInfo(rocketId, description)
            },
        )

        rocketInfoScreen(
            onBack = { navController.popBackStack() },
        )
    }
}
