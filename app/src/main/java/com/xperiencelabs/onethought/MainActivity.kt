package com.xperiencelabs.onethought

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.SplashScreen
import com.xperiencelabs.onethought.presenter.authentication.AuthenticationViewModel
import com.xperiencelabs.onethought.presenter.authentication.LoginScreen
import com.xperiencelabs.onethought.presenter.authentication.SignUpScreen
import com.xperiencelabs.onethought.presenter.screens.home.HomeScreen
import com.xperiencelabs.onethought.presenter.screens.favorite.Favorite
import com.xperiencelabs.onethought.presenter.screens.home.HomeViewModel
import com.xperiencelabs.onethought.presenter.screens.home.PostScreen
import com.xperiencelabs.onethought.presenter.screens.home.components.UploadImage
import com.xperiencelabs.onethought.presenter.screens.profile.EditProfile
import com.xperiencelabs.onethought.presenter.screens.profile.ProfileScreen
import com.xperiencelabs.onethought.presenter.screens.search.Search
import com.xperiencelabs.onethought.ui.theme.OneThoughtTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            OneThoughtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                        val navController = rememberNavController()
                        val authViewModel:AuthenticationViewModel = hiltViewModel()
                    OneThoughtApp(navController = navController, authViewModel = authViewModel )
                }
            }
        }
    }
}

@Composable
fun OneThoughtApp(navController: NavHostController,authViewModel:AuthenticationViewModel) {
    NavHost(navController = navController, startDestination = ScreenRoutes.SplashScreen.route){

        composable(route = ScreenRoutes.SplashScreen.route){
            SplashScreen(navController,authViewModel)
        }

        composable(route = ScreenRoutes.LoginScreen.route){
            LoginScreen(navController,authViewModel)
        }
        composable(route = ScreenRoutes.SignUpScreen.route){
            SignUpScreen(navController,authViewModel)
        }
        composable(route = ScreenRoutes.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = ScreenRoutes.ProfileScreen.route){
            ProfileScreen(navController, authViewModel = authViewModel)
        }
        composable(route = ScreenRoutes.FavoriteScreen.route){
            Favorite(navController)
        }
        composable(route = ScreenRoutes.SearchScreen.route){
            Search(navController)
        }
        composable(route = ScreenRoutes.EditProfileScreen.route){
            EditProfile()
        }
        composable(route = ScreenRoutes.PostScreen.route){
            PostScreen(navController= navController)
//            UploadImage()
        }

    }
}

