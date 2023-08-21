package com.example.hero_interactors

import com.example.core.Logger
import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.hero_datasource_test.cache.HeroCacheFake
import com.example.hero_datasource_test.cache.HeroDatabaseFake
import com.example.hero_datasource_test.network.HeroServiceFake
import com.example.hero_datasource_test.network.HeroServiceResponseType
import com.example.hero_datasource_test.network.data.HeroDataValid.NUM_HEROS
import com.example.hero_domain.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetHeroTest {
    // system in test
    private lateinit var getHeros: GetHeros

    @Test
    fun getHeros_succes() = runBlocking(Dispatchers.IO){
        //setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake().build(
            type = HeroServiceResponseType.GoodData
        )

        getHeros = GetHeros(
            heroService,
            Logger( tag = "HeroList", isDebug = true),
            heroCache
        )

        // confirm the cache is empty before any use-case has been executed

        var cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        // Execute use-case
        val emmision = getHeros.execute().toList()

        //firs emmision should be loading
        assert(emmision[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // confirm the second emession is data
        assert(emmision[1] is DataState.Data)
        assert(((emmision[1] as DataState.Data).data?.size ?: 0) == NUM_HEROS)


        //confirm the cache is no loger empty
        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.size == NUM_HEROS)


        //third emmision should be loading idle
        assert(emmision[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))

    }
}