package com.datienza.spacex.presentation.router

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.datienza.spacex.presentation.view.MainActivity
import com.datienza.spacex.presentation.view.RocketsFragmentDirections
import com.test.emptyapplication.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MainActivityRouterImpl @Inject constructor(
    @ActivityContext private val context: Context
) : MainActivityRouter,
    NavigateBackRouter {

    private val activity = context as MainActivity

    private val navController: NavController
        get() = activity.findNavController(R.id.nav_host_fragment)

    override fun finish() {
        activity.finish()
    }

    override fun goBack() {
        if (!navController.popBackStack()) {
            finish()
        }
    }

    override fun goToRocketInfo(rocketId: String, rocketDescription: String) {
        val action = RocketsFragmentDirections
            .actionRocketsFragmentToRocketInfoFragment(rocketId, rocketDescription)
        navController.navigate(action)
    }
}