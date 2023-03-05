package com.example.multimoduleapphero.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.memory.MemoryCache
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.hero_interactors.HeroInteractors
import com.example.multimoduleapphero.ui.theme.MultiModuleAppHeroTheme
import com.example.ui_herolist.HeroList
import com.example.ui_herolist.R.*
import com.example.ui_herolist.ui.HeroListState
import com.example.ui_herolist.ui.HeroListViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var imageLoader:ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         imageLoader = ImageLoader.Builder(applicationContext)
            .error(drawable.error_image)
            .placeholder(drawable.white_background)
            .memoryCache {
                MemoryCache.Builder(applicationContext).maxSizePercent(0.25).build()
            }
            .crossfade(true)
            .build()
        setContent {
            MultiModuleAppHeroTheme {
                val viewmodel: HeroListViewModel  = hiltViewModel()
                HeroList(
                    state = viewmodel.state.value,
                    imageLoader)
            }
        }
    }
}
