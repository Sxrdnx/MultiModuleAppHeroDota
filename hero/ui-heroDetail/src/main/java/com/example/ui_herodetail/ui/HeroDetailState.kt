package com.example.ui_herodetail.ui

import com.example.core.ProgressBarState
import com.example.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero ? = null,

)