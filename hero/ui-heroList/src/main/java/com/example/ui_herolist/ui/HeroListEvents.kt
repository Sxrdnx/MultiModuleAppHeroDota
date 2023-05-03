package com.example.ui_herolist.ui

import com.example.core.domain.UIComponentState
import com.example.hero_domain.HeroFilter

sealed class HeroListEvents{
    object GetHeros: HeroListEvents()
    object FilterHeros: HeroListEvents()

    data class UpdateHeroName(
        val name: String,
    ): HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ):HeroListEvents()


    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState
    ): HeroListEvents()
}
