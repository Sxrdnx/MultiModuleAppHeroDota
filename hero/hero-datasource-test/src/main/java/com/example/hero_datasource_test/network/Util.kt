package com.example.hero_datasource_test.network

import com.example.hero_datasource.netwok.HeroDto
import com.example.hero_datasource.netwok.toHero
import com.example.hero_domain.Hero
import kotlinx.serialization.json.Json

private val json  = Json {
    ignoreUnknownKeys = true
}

fun serializeHeroData(jsonData: String): List<Hero>{
    return   json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
}