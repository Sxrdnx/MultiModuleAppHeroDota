package com.example.ui_herolist.ui

import com.example.core.ProgressBarState
import com.example.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
    val filteredHeros : List<Hero> = listOf(),
    val heroName: String = "",

)
