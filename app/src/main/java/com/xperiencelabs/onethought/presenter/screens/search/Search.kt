package com.xperiencelabs.onethought.presenter.screens.search

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.screens.profile.components.BottomBar
import com.xperiencelabs.onethought.presenter.screens.profile.components.NavButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Search(navController:NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
        },
        bottomBar = {
            BottomBar(items = listOf(
                NavButton(
                    purpose = "Home",
                    route = ScreenRoutes.HomeScreen.route,
                    UnSelectedIcon = R.drawable.ic_outline_home_24,
                    SelectedIcon = R.drawable.ic_baseline_home_24
                ),
                NavButton(
                    purpose = "Search",
                    route = ScreenRoutes.SearchScreen.route,
                    UnSelectedIcon = R.drawable.ic_baseline_search_24,
                    SelectedIcon = R.drawable.ic_baseline_search_24
                ),
                NavButton(
                    purpose = "Like",
                    route = ScreenRoutes.FavoriteScreen.route,
                    UnSelectedIcon = R.drawable.ic_outline_favorite_border_24,
                    SelectedIcon = R.drawable.ic_baseline_favorite_24
                ),
                NavButton(
                    purpose = "Profile",
                    route = ScreenRoutes.ProfileScreen.route,
                    UnSelectedIcon = R.drawable.ic_outline_person_outline_24,
                    SelectedIcon = R.drawable.ic_baseline_person_24
                )
            ) ,

                navController = navController,
                onItemClick = {
                    navController.navigate(it.route){
                        popUpTo(ScreenRoutes.HomeScreen.route){
                            inclusive = true
                        }
                    }
                })
        }
    ) {
        Text(text = "Hi this is Search")
    }
}