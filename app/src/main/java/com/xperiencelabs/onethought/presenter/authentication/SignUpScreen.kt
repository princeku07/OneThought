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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.utils.Toast
import com.xperiencelabs.onethought.utils.Response

@Composable
fun SignUpScreen(
    navController: NavController,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                var email by remember {
                    mutableStateOf("")
                }
            var password by remember {
                mutableStateOf("")
            }
            var name by remember {
                mutableStateOf("")
            }

            Image(
                    painter = painterResource(id = com.xperiencelabs.onethought.R.drawable.one_thought),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 16.dp)
                        .padding(8.dp)
                )

                androidx.compose.material.Text(
                    text = "Sign Up",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h4
                )
            OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = {
                     Text(text = "Enter Email")
                    },
                    modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(10.dp)
                )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
            label = {
                Text(text = "Enter Name")
            },
            modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(10.dp))
               OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text(text = "Enter Password")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.padding(10.dp),
                   shape = RoundedCornerShape(10.dp)
                )

                Button(onClick = {
                    viewModel.signUp(email,password,name)
                }, modifier = Modifier.padding(8.dp)) {
                    Text(text = "Register")
                    when (val response = viewModel.signUpState.value) {
                        is Response.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        is Response.Success -> {
                            if (response.data!!) {
                                //will not be affected by recomposition
                                LaunchedEffect(key1 = true){
                                    navController.navigate(ScreenRoutes.ProfileScreen.route) {
                                        //login screen will be removed from backstack after login
                                        popUpTo(
                                            ScreenRoutes.LoginScreen.route
                                        ) {
                                            inclusive = true
                                        }
                                    }
                                }

                            } else {
                                Toast(message = "Sign Up failed")
                            }
                        }
                        is Response.Error -> {
                            response.message?.let {
                                Toast(
                                    message = it
                                )
                            }
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