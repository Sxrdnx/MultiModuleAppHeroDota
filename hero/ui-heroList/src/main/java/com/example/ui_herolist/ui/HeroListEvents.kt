package com.example.ui_herolist.ui

sealed class HeroListEvents{
    object GetHeros: HeroListEvents()
    object FilterHeros: HeroListEvents()

    data class UpdateHeroName(
        val name: String,
    ): HeroListEvents()
}
