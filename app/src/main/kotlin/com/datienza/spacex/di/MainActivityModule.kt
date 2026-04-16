package com.datienza.spacex.di

import com.datienza.spacex.core.common.router.MainActivityRouter
import com.datienza.spacex.core.common.router.NavigateBackRouter
import com.datienza.spacex.presentation.router.MainActivityRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
interface MainActivityModule {

    @Binds
    fun bindsMainActivityRouter(mainActivityRouterImpl: MainActivityRouterImpl): MainActivityRouter

    @Binds
    fun bindsNavigateBackRouter(mainActivityRouterImpl: MainActivityRouterImpl): NavigateBackRouter
}