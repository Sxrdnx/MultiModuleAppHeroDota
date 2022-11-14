package com.example.hero_datasource.netwok

import com.example.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.request
import io.ktor.http.Url

class HeroServiceImpl(
    private val httpClient: HttpClient
):HeroService {
    override suspend fun getHeroStats(): List<Hero> {
        return  httpClient.get(EndPoints.HERO_STATS)
            .body<List<HeroDto>>()
            .map { it.toHero() }
    }
}