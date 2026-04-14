package com.datienza.spacex.core.common.router

interface MainActivityRouter {
    fun finish()
    fun goToRocketInfo(rocketId: String, rocketDescription: String)
}
