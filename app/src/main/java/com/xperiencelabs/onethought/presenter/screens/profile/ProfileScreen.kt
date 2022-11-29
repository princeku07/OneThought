package com.xperiencelabs.onethought.presenter.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.authentication.AuthenticationViewModel
import com.xperiencelabs.onethought.presenter.screens.profile.components.*
import com.xperiencelabs.onethought.presenter.utils.Toast
import com.xperiencelabs.onethought.utils.Response


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController:NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    authViewModel:AuthenticationViewModel,

) {
    viewModel.userProfileInfo()
    val userProfile = viewModel.userProfile.value
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
        Column(modifier = Modifier.fillMaxSize()) {

                userProfile.userProfile?.let { user ->
                    TopAppBar(
                        title = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ){

                                Text(text = user.name)
                            }
                        },
                        backgroundColor = Color.White,
                        actions = {
                            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                IconButton(onClick = {

                                }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "search")
                                }
                                IconButton(onClick = {
                                       authViewModel.signOut()

                                }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_baseline_logout_24), contentDescription = "search")
                                    when(val response = authViewModel.signOutState.value){
                                        is Response.Loading -> {
                                            CircularProgressIndicator()
                                        }
                                        is Response.Success -> {
                                            if (response.data!!){
                                                LaunchedEffect(key1 = true){

                                                    navController.navigate(ScreenRoutes.LoginScreen.route){
                                                        popUpTo(ScreenRoutes.ProfileScreen.route){
                                                            inclusive = true
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        is Response.Error -> {
                                            response.message?.let {
                                                Toast(message = it)
                                            }

                                        }
                                    }
                                }


                            }
                        }, elevation = 0.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileSection(user = user,navController = navController, userIsMe = true)
                }

        }



    }

}













/*
//        viewModel.setUserName("prince")
viewModel.userProfileInfo()
val userProfile = viewModel.userProfile.value
Box(modifier = Modifier.fillMaxSize()){
    userProfile.userProfile?.let {
        Text(text = "hi ${it.name}")
        Column {
            Button(onClick = {
                authViewModel.signOut()
            }, modifier = Modifier.padding(8.dp)) {
                 Text(text = "Sign Out")


            }
        }
    }
}
 */







