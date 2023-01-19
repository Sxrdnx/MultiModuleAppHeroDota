package com.example.multimoduleapphero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.hero_domain.Hero
import com.example.hero_interactors.HeroInteractors
import com.example.multimoduleapphero.ui.theme.MultiModuleAppHeroTheme
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val heros: MutableState<List<Hero>> = mutableStateOf(listOf())
    private val progressBarState:  MutableState<ProgressBarState> = mutableStateOf(ProgressBarState.Idle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    heros.value =  dataState.data?:listOf()
                }
                is DataState.Loading->{
                    progressBarState.value = dataState.progressBarState
                }
            }

        }.launchIn(CoroutineScope(IO))

        setContent {
            MultiModuleAppHeroTheme {
               Box(modifier = Modifier.fillMaxSize()){
                   LazyColumn{
                       items(heros.value){hero ->
                           Text(text = hero.localizedName)
                       }
                   }
                   if  (progressBarState.value is ProgressBarState.Loading){
                       CircularProgressIndicator(
                           modifier = Modifier.align(Alignment.Center)
                       )
                   }
               }

            }
        }
    }
}
