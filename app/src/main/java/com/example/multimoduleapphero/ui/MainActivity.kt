package com.example.multimoduleapphero.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.memory.MemoryCache
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.ProgressBarState
import com.example.core.UIComponent
import com.example.hero_interactors.HeroInteractors
import com.example.multimoduleapphero.ui.navigation.Screen
import com.example.multimoduleapphero.ui.theme.MultiModuleAppHeroTheme
import com.example.ui_herodetail.HeroDetail
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var imageLoader:ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiModuleAppHeroTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HeroList.route,
                    builder = {
                        addHeroList(navController,imageLoader)
                        addHeroDetail()
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader
){
    composable(
        route = Screen.HeroList.route
    ){
        val viewmodel: HeroListViewModel  = hiltViewModel()
        HeroList(
            state = viewmodel.state.value,
            imageLoader,
            navigateToDetailScreen = {heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }

}

fun NavGraphBuilder.addHeroDetail(){
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments
    ){ navBackStackEntry ->
        HeroDetail(
            heroId = navBackStackEntry.arguments?.getInt("heroId")
        )
    }
}