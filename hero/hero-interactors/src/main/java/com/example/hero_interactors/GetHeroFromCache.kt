package com.example.hero_interactors

import com.example.core.DataState
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroFromCache(
    private val cache: HeroCache,

) {

    fun execute(
        id:Int,
    ): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val cachedHero = cache.getHero(id) ?: throw Exception("That Hero does not exist in the cache")
            emit(DataState.Data(cachedHero))

        }catch (e:Exception){
            e.printStackTrace()

            emit(DataState.Response<Hero>(
                uiComponent = UIComponent.Dialog(
                    title = "Error",
                    description = e.message?: "Unknown Error"
                )
            ))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }


}