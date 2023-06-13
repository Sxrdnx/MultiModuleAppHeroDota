package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.Logger
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.netwok.HeroService
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetHeros(
    private val service: HeroService,
    private val logger:Logger,
    private val cache: HeroCache
) {
    fun execute():Flow<DataState<List<Hero>>> =flow{
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val heros : List<Hero> = try {
                service.getHeroStats()
            }catch (e:Exception){
                e.printStackTrace()
                emit(
                    DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Network Data Error",
                        description = e.message?: "Unknown Error"
                    )
                ))
                emptyList<Hero>()
            }

            //cache the network data
            cache.insert(heros)
            //emit data from cache
            val cacheHeros = cache.selectAll()

            emit(DataState.Data(cacheHeros))
            throw Exception("Somthing went wrong")


        }catch (e:Exception){
            e.printStackTrace()
            logger.log(e.message?:"Unknown")
            emit(
                DataState.Response(
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