package com.example.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponentState
import com.example.ui_herolist.components.HeroListFilter
import com.example.ui_herolist.components.HeroListItem
import com.example.ui_herolist.components.HeroListToolbar

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
               onShowFilterDialog = {
                   events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
               }
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

        if (state.filterDialogState is UIComponentState.Show){
            HeroListFilter(
                heroFilter= state.heroFilter,
                onUpdateHeroFilter = {heroFilter ->
                    events(HeroListEvents.UpdateHeroFilter(heroFilter))

                },
                attributeFilter = state.primaryAttribute,
                onUpdateAttributeFilter = {heroAttribute ->
                    events(HeroListEvents.UpdateHeroAttributeFilter(heroAttribute))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(uiComponentState = UIComponentState.Hide))
                }
            )
        }


        if  (state.progressBarState is ProgressBarState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


}