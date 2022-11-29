package com.xperiencelabs.onethought.domain.use_cases.authentication

data class Authentication (
    val UserAuthenticationState:UserAuthenticationState,
    val FirebaseAuthState:FirebaseAuthState,
    val SignIn:SignIn,
    val SignUp:SignUp,
    val SignOut: SignOut
        )