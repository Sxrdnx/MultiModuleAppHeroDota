package com.example.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Logger
import com.example.core.domain.DataState
import com.example.core.domain.Queue
import com.example.core.domain.UIComponent
import com.example.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewmodel @Inject constructor(
    private val getHeroFromCache: GetHeroFromCache,
    private val savedStateHandle: SavedStateHandle,
   // private val logger: Logger,
): ViewModel() {
     val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let {heroId ->
            onTriggerEvent(HeroDetailEvents.GetHeroFroCache(heroId))
        }
    }
     fun onTriggerEvent(events: HeroDetailEvents){
        when(events){
            is HeroDetailEvents.GetHeroFroCache -> {
                getHeroFromCache(events.id)
            }
            is HeroDetailEvents.OnRemoveHeadFromQueue->{
                removeHeadMessage()
            }
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroFromCache.execute(id).onEach {dataState ->
            when(dataState){
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Response -> {

                    when(dataState.uiComponent){
                        is UIComponent.Dialog->{
                            appendToMessageQueue(dataState.uiComponent)
                       //     logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None->{
                         //   logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun appendToMessageQueue(uiComponent: UIComponent){
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy( errorQueue =  Queue(mutableListOf())) // force to recompose
        state.value = state.value.copy( errorQueue = queue)
    }
    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy( errorQueue =  Queue(mutableListOf())) // force to recompose
            state.value = state.value.copy( errorQueue = queue)
        }catch (e: Exception){
     //       logger.log("nothing to remove from DialogQueue")
        }
    }

}