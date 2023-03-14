package com.example.ui_herodetail.ui

sealed class HeroDetailEvents{
    data class GetHeroFroCache(val id: Int, ): HeroDetailEvents()
}
