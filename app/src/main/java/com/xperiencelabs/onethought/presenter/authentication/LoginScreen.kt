package com.xperiencelabs.onethought.presenter.authentication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.xperiencelabs.onethought.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.utils.Toast
import com.xperiencelabs.onethought.utils.Response


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel:AuthenticationViewModel
    ) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            var email by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }
            
            Image(painter = painterResource(id = R.drawable.one_thought), contentDescription = "Logo",
            modifier = Modifier
                .width(250.dp)
                .padding(top = 40.dp)
                .padding(8.dp))
            
            Text(text = "Sign In",
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.h4)
            OutlinedTextField(value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = "Enter Your Email")
                },
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(15.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Enter Your Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(15.dp)
            )

            Button(onClick = {
                    viewModel.signIn(email,password)
            }, modifier = Modifier.padding(8.dp)) {
                    Text(text = "Sign In")
                    when(val response = viewModel.signInState.value){
                        is Response.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        is Response.Success ->{

                                if (response.data!!){
                                    LaunchedEffect(key1 = true){navController.navigate(ScreenRoutes.HomeScreen.route){
                                        //login screen will be removed from backstack after login
                                        popUpTo(
                                            ScreenRoutes.LoginScreen.route
                                        ){
                                            inclusive = true
                                        }
                                    }
                                    }


                                }
                            else{

                                Toast(message = "Sign In failed")
                                }
                        }
                        is Response.Error -> {
                            response.message?.let { Toast(message = it) }
                        }
                    }
            }
            Text(text = "New User? Sign Up",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(route = ScreenRoutes.SignUpScreen.route) {
                            launchSingleTop = true
                        }
                    })
        }
    }

}