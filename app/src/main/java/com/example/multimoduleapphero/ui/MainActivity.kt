package com.example.multimoduleapphero.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import coil.ImageLoader
import coil.memory.MemoryCache
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.hero_interactors.HeroInteractors
import com.example.multimoduleapphero.ui.theme.MultiModuleAppHeroTheme
import com.example.ui_herolist.HeroList
import com.example.ui_herolist.R.*
import com.example.ui_herolist.ui.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private val progressBarState:  MutableState<ProgressBarState> = mutableStateOf(ProgressBarState.Idle)
    private lateinit var imageLoader:ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         imageLoader = ImageLoader.Builder(applicationContext)
            .error(drawable.error_image)
            .placeholder(drawable.white_background)
            .memoryCache {
                MemoryCache.Builder(applicationContext).maxSizePercent(0.25).build()
            }
            .crossfade(true)
            .build()


        val getHeros = HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.dbName
            )
        ).getHeros
        val logger = Logger("GET-HEROS")
        getHeros.execute().onEach { dataState->
            when(dataState){
                is DataState.Response -> {
                    when(dataState.uiComponent){
                        is UIComponent.Dialog->{
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None->{
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data->{
                    state.value =  state.value.copy(heros = dataState.data?:listOf())
                }
                is DataState.Loading->{
                    progressBarState.value = dataState.progressBarState
                }
            }

        }.launchIn(CoroutineScope(IO))

        setContent {
            MultiModuleAppHeroTheme {
                HeroList(state = state.value,imageLoader)
            }
        }
    }
}
