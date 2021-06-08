package com.datienza.spacex.presentation.router

interface MainActivityRouter {
    fun finish()
    fun goToRocketInfo(rocketId: String, rocketDescription: String)
}