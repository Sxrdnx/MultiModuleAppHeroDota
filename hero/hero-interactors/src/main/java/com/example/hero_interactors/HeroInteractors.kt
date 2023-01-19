package com.example.hero_interactors

import com.example.core.Logger
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.netwok.HeroService
import com.squareup.sqldelight.db.SqlDriver

class HeroInteractors(
    val getHeros: GetHeros,
    //TODO(Add other interactors)
) {
    companion object Factory{
        fun build(sqlDriver: SqlDriver):HeroInteractors{
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeros = GetHeros(
                    service  = service,
                    logger = Logger("GET-HEROS"),
                    cache =cache
                )
            )
        }
        val schema : SqlDriver.Schema = HeroCache.schema
        val dbName: String = HeroCache.dbName
    }
}