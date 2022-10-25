package com.example.hero_datasource.netwok

import com.example.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.request.*

class HeroServiceImpl(
    private val httpClient: HttpClient
):HeroService{
    override suspend fun getHeroStats(): List<Hero> {
       val x =httpClient.get<List<HeroDto>>(EndPoints.HERO_STATS)
        return x.map{}
    }
}