package com.example.ui_herodetail.ui

import androidx.lifecycle.ViewModel
import com.example.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewmodel @Inject constructor(
    private val getHeroFromCache: GetHeroFromCache
): ViewModel() {

}