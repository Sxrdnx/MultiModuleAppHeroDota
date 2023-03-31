package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.FilterOrder
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter
import kotlinx.coroutines.flow.Flow
import kotlin.math.round

class FilterHeros {

    operator fun invoke(
        currentList:List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        attributeFilter: HeroAttribute
    ): List<Hero>{
        var filteredList: MutableList<Hero> = currentList.filter { hero ->
            hero.localizedName.lowercase().contains(heroName.lowercase())
        }.toMutableList()

        when(heroFilter){
            is HeroFilter.Hero->{
                when(heroFilter.order){
                    is FilterOrder.Descending->{
                        filteredList.sortByDescending { it.localizedName }
                    }

                    is FilterOrder.Ascending->{
                        filteredList.sortBy{ it.localizedName }

                    }
                }
            }

            is HeroFilter.ProWins->{
                when(heroFilter.order){
                    is FilterOrder.Descending->{
                        filteredList.sortByDescending {
                            getWinRate(it.proWins.toDouble(),it.proPick.toDouble())
                        }
                    }

                    is FilterOrder.Ascending->{
                        filteredList.sortBy{
                            getWinRate(it.proWins.toDouble(),it.proPick.toDouble())
                        }

                    }
                }
            }

        }

        when(attributeFilter){
            is HeroAttribute.Strength->{
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Strength}.toMutableList()
            }
            is HeroAttribute.Agility->{
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Agility}.toMutableList()
            }
            is HeroAttribute.Intelligence->{
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Intelligence}.toMutableList()
            }
            is HeroAttribute.Unknown->{
                //do not filter

            }

        }


        return filteredList

    }


    private fun getWinRate(proWins: Double, proPick:Double): Int{
        return if (proPick <= 0){
            0
        }else{
            val winRate = round(proWins / proPick* 100).toInt()
            winRate
        }
    }
}