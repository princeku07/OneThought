package com.xperiencelabs.onethought.presenter


import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.presenter.authentication.AuthenticationViewModel
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthenticationViewModel
) {
    val authValue = authViewModel.isUserAuthenticated
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = { OvershootInterpolator(2f).getInterpolation(it) }
            )
        )
        delay(3000)
        if (authValue){
            navController.navigate(ScreenRoutes.HomeScreen.route){
                //removing splash screen from backstack
                popUpTo(ScreenRoutes.SplashScreen.route){
                    inclusive = true
                }
            }
        }
        else{
            navController.navigate(ScreenRoutes.LoginScreen.route){
                popUpTo(ScreenRoutes.SplashScreen.route){
                    inclusive = true
                }
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.one_thought), contentDescription = "logo",
        modifier=Modifier.scale(scale.value))
    }
}