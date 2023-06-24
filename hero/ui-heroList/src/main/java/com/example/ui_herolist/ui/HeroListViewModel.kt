package com.example.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.DataState
import com.example.core.Logger
import com.example.core.domain.Queue
import com.example.core.domain.UIComponent
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter
import com.example.hero_interactors.FilterHeros
import com.example.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel
@Inject constructor(
    private val getHeros: GetHeros,
    private val filterHeros: FilterHeros,
    private @Named("heroListLogger") val logger:Logger
):ViewModel(){


    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    init{
        onTrigerEvent(event = HeroListEvents.GetHeros)
    }


    fun onTrigerEvent(event: HeroListEvents){
        when (event) {
            is HeroListEvents.GetHeros -> {
                getHeros()
            }
            is HeroListEvents.FilterHeros -> {
                filterHeros()
            }
            is HeroListEvents.UpdateHeroName -> {
                updateHeroName(event.name)
            }
            is HeroListEvents.UpdateHeroFilter->{
                updateHeroFilter(event.heroFilter)
            }
            is HeroListEvents.UpdateFilterDialogState->{
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }
            is HeroListEvents.UpdateHeroAttributeFilter->{
                updateAttributeFilter(event.attribute)
            }
            is HeroListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
        }
    }

    private fun updateAttributeFilter(attribute: HeroAttribute) {
        state.value = state.value.copy(primaryAttribute = attribute)
        filterHeros()
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        state.value = state.value.copy(heroFilter = heroFilter)
        filterHeros()

    }

    private fun updateHeroName(heroName: String) {
        state.value = state.value.copy(heroName= heroName)
    }

    private fun filterHeros() {
        val filteredList = filterHeros.invoke(
            state.value.heros,
            state.value.heroName,
            state.value.heroFilter,
            state.value.primaryAttribute
        )
        state.value = state.value.copy( filteredHeros=filteredList )

    }

    private fun getHeros(){
        getHeros.execute().onEach { dataState->
            when(dataState){
                is DataState.Response -> {
                    when(dataState.uiComponent){
                        is UIComponent.Dialog->{
                            appendToMessageQueue(dataState.uiComponent)
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None->{
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data->{
                    state.value =  state.value.copy(heros = dataState.data?:listOf())
                    filterHeros()
                }
                is DataState.Loading->{
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
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
            logger.log("nothing to remove from DialogQueue")
        }
    }
}
