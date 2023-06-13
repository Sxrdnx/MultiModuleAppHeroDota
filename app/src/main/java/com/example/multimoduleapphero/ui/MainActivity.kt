package com.example.multimoduleapphero.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.example.multimoduleapphero.ui.navigation.Screen
import com.example.multimoduleapphero.ui.theme.MultiModuleAppHeroTheme
import com.example.ui_herodetail.ui.HeroDetail
import com.example.ui_herodetail.ui.HeroDetailViewmodel
import com.example.ui_herolist.ui.HeroList
import com.example.ui_herolist.ui.HeroListViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var imageLoader:ImageLoader
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiModuleAppHeroTheme {
                val navController = rememberAnimatedNavController()
                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.HeroList.route,
                        builder = {
                            addHeroList(
                                navController,
                                imageLoader,
                                width = constraints.maxWidth / 2
                            )
                            addHeroDetail(
                                imageLoader,
                                width = constraints.maxWidth / 2
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int
){
    composable(
        route = Screen.HeroList.route,
        exitTransition ={
            slideOutHorizontally(
                targetOffsetX ={-width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally (
                initialOffsetX = {-width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                    )
            ) + fadeIn(animationSpec = tween(300))
        }
    ){
        val viewmodel: HeroListViewModel  = hiltViewModel()
        HeroList(
            state = viewmodel.state.value,
            viewmodel::onTrigerEvent,
            imageLoader,
            navigateToDetailScreen = {heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }

}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHeroDetail(
    imageLoader: ImageLoader,
    width: Int,
){
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments,
        enterTransition ={
            slideInHorizontally (
                initialOffsetX = {width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        } ,
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX ={width},
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
    ){ navBackStackEntry ->
        val viewmodel: HeroDetailViewmodel = hiltViewModel()
        //heroId = navBackStackEntry.arguments?.getInt("heroId")
        HeroDetail(
            state = viewmodel.state.value,
            imageLoader = imageLoader,
            events = viewmodel::onTriggerEvent,
        )
    }
}



