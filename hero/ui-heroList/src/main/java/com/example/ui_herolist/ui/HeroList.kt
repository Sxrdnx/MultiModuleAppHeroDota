package com.example.ui_herolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.ProgressBarState
import com.example.ui_herolist.components.HeroListItem
import com.example.ui_herolist.ui.HeroListState

@Composable
fun HeroList(
    state: HeroListState,
){

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn{
            items(state.heros){hero ->
                HeroListItem(
                    hero = hero,
                    onSelectHero ={

                    }
                )

            }
        }
        if  (state.progressBarState is ProgressBarState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


}