package com.example.ui_herolist.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.components.DefaultScreenUI
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
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(HeroListEvents.OnRemoveHeadFromQueue )
        },
        progressBarState = state.progressBarState
    ) {
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

    }
}