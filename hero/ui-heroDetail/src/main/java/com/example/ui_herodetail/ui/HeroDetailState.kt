package com.example.ui_herodetail.ui

import com.example.core.domain.ProgressBarState
import com.example.core.domain.Queue
import com.example.core.domain.UIComponent
import com.example.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero ? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
    )