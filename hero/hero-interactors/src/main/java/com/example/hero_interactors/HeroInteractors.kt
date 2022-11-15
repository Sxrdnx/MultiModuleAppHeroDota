package com.example.hero_interactors

import com.example.core.Logger
import com.example.hero_datasource.netwok.HeroService

class HeroInteractors(
    val getHeros: GetHeros,
    //TODO(Add other interactors)
) {
    companion object Factory{
        fun build():HeroInteractors{
            val service = HeroService.build()
            return HeroInteractors(
                getHeros = GetHeros(
                    service  = service,
                    logger = Logger("GET-HEROS")
                )
            )
        }
    }
}