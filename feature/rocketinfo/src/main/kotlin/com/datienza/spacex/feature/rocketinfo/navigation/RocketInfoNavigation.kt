package com.datienza.spacex.feature.rocketinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.datienza.spacex.feature.rocketinfo.ui.RocketInfoRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val ROCKET_INFO_ROUTE = "rocketinfo/{rocketId}/{rocketDescription}"

fun NavController.navigateToRocketInfo(rocketId: String, rocketDescription: String) {
    val encodedDesc = URLEncoder.encode(rocketDescription, StandardCharsets.UTF_8.toString())
    navigate("rocketinfo/$rocketId/$encodedDesc")
}

fun NavGraphBuilder.rocketInfoScreen(
    onBack: () -> Unit,
) {
    composable(
        route = ROCKET_INFO_ROUTE,
        arguments = listOf(
            navArgument("rocketId") { type = NavType.StringType },
            navArgument("rocketDescription") { type = NavType.StringType },
        ),
    ) {
        RocketInfoRoute(onBack = onBack)
    }
}
