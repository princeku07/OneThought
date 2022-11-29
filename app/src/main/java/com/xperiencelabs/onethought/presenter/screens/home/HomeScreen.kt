package com.xperiencelabs.onethought.presenter.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.domain.model.Post
import com.xperiencelabs.onethought.presenter.screens.home.HomeViewModel
import com.xperiencelabs.onethought.presenter.screens.home.components.ThoughtPost
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.screens.profile.ProfileViewModel
import com.xperiencelabs.onethought.presenter.screens.profile.components.BottomBar
import com.xperiencelabs.onethought.presenter.screens.profile.components.NavButton
import com.xperiencelabs.onethought.presenter.screens.profile.components.Notification
import com.xperiencelabs.onethought.ui.theme.interactionBg
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun HomeScreen(navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                TopAppBar(
                    title = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ){

                            Image(painter = painterResource(id = R.drawable.logo_one), contentDescription = null)
                            Image(painter = painterResource(id = R.drawable.logo_text), contentDescription = null, modifier = Modifier.padding(start = 2.dp))
                        }
                    },
                    backgroundColor = Color.White,
                    actions = {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            IconButton(onClick = { }) {
                                Notification(navButton = NavButton(
                                    purpose = "notification",
                                    route = ScreenRoutes.HomeScreen.route,
                                    SelectedIcon = R.drawable.ic_outline_notifications_24,
                                    UnSelectedIcon = R.drawable.ic_outline_notifications_24,
                                    badgeCount = 6
                                ))
                            }
                        }
                    }
                )
        },
        floatingActionButton = {
                               FloatingActionButton(onClick = {
                                   navController.navigate(ScreenRoutes.PostScreen.route){
                                       popUpTo(ScreenRoutes.PostScreen.route){
                                           inclusive = true
                                       }
                                   }
                               }, contentColor = Color.Yellow, backgroundColor = interactionBg) {
                                     Icon(painter = painterResource(id = R.drawable.lit_bulb), contentDescription = null,
                                         modifier = Modifier.size(30.dp))
                               }
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
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                })
        }
    ) {
        viewModel.getAllPost()
        val state = viewModel.posts.value
        var selectedIndex by remember{mutableStateOf(-1)}
        Surface {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp, top = 20.dp)
            ) {
                items(state.posts){post ->

                    ThoughtPost(post = post, viewModel = viewModel)

                    Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(end = 8.dp))
                    Log.e("Fuck Ui Post", post.postId)
                }

                








            }
        }


    }

}






