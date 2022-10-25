package com.example.hero_datasource.netwok

import com.example.hero_domain.Hero

interface HeroService {
    suspend fun getHeroStats():List<Hero>
}