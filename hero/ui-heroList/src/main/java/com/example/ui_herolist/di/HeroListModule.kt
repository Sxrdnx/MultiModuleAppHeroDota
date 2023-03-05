package com.example.ui_herolist.di

import androidx.navigation.Navigator
import com.example.core.Logger
import com.example.hero_interactors.GetHeros
import com.example.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "HeroList",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHeros(
        interactors: HeroInteractors
    ):GetHeros{
        return interactors.getHeros
    }

}