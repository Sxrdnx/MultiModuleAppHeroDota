package com.example.multimoduleapphero.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.example.multimoduleapphero.ui.navigation.Screen
import com.example.multimoduleapphero.ui.theme.MultiModuleAppHeroTheme
import com.example.ui_herodetail.ui.HeroDetail
import com.example.ui_herodetail.ui.HeroDetailViewmodel
import com.example.ui_herolist.HeroList
import com.example.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
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
                        addHeroDetail(imageLoader)
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

fun NavGraphBuilder.addHeroDetail(
    imageLoader: ImageLoader
){
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments
    ){ navBackStackEntry ->
        val viewmodel: HeroDetailViewmodel = hiltViewModel()
        //heroId = navBackStackEntry.arguments?.getInt("heroId")
        HeroDetail(state = viewmodel.state.value,
        imageLoader = imageLoader)
    }
}