package com.example.ui_herolist.ui

import com.example.core.domain.ProgressBarState
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
    val filteredHeros : List<Hero> = listOf(),
    val heroName: String = "",
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown
    )
