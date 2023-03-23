package com.example.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.core.ProgressBarState
import com.example.ui_herolist.components.HeroListItem
import com.example.ui_herolist.components.HeroListToolbar
import com.example.ui_herolist.ui.HeroListState

@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen:(Int)-> Unit
){

    Box(modifier = Modifier.fillMaxSize()){
       Column {
           HeroListToolbar(
               heroName = state.heroName,
               onHeroNameChanged ={ heroName->
                   events(HeroListEvents.UpdateHeroName(heroName))
               } ,
               onExecuteSearch = {
                   events(HeroListEvents.FilterHeros)
               },
               onShowFilterDialog = {}
           )



           LazyColumn(
               modifier = Modifier
                   .fillMaxSize()
           ){
               items(state.filteredHeros){hero ->
                   HeroListItem(
                       hero = hero,
                       onSelectHero ={ heroID ->
                           navigateToDetailScreen(heroID)
                       },
                       imageLoader = imageLoader
                   )

               }
           }
       }
        if  (state.progressBarState is ProgressBarState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


}