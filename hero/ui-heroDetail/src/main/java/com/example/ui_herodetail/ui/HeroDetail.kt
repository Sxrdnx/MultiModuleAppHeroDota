package com.example.ui_herodetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.ui_herodetail.ui.HeroDetailState

@Composable
fun HeroDetail(
    state: HeroDetailState
){
    state.hero?.let {
        Text(text = "Hero id ${it.localizedName}")
    } ?: Text(text = "LOADING...")

}