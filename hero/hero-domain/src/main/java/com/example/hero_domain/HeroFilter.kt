package com.example.hero_domain

import com.example.core.domain.FilterOrder

sealed class HeroFilter(val uiValue: String){
    data class Hero(
        val oder:FilterOrder = FilterOrder.Descending
    ): HeroFilter("Hero")


    data class ProWins(
        val oder:FilterOrder = FilterOrder.Descending
    ): HeroFilter("Pro Win-rate")

}
