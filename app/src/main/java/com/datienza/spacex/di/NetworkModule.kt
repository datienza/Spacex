package com.datienza.spacex.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.emptyapplication.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context, gson: Gson): Retrofit {
        val client = OkHttpClient.Builder().build()
        val rxFactory = RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
            .client(client)
            .baseUrl(context.getString(R.string.base_url))
            .addCallAdapterFactory(rxFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}