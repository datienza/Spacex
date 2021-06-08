package com.datienza.spacex.di

import com.datienza.spacex.presentation.router.MainActivityRouter
import com.datienza.spacex.presentation.router.MainActivityRouterImpl
import com.datienza.spacex.presentation.router.NavigateBackRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
interface MainActivityModule {

    @ActivityScoped
    @Binds
    fun bindsMainActivityRouter(mainActivityRouterImpl: MainActivityRouterImpl): MainActivityRouter

    @ActivityScoped
    @Binds
    fun bindsNavigateBackRouter(mainActivityRouterImpl: MainActivityRouterImpl): NavigateBackRouter
}