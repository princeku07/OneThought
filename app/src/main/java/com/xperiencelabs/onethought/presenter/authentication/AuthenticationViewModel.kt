package com.xperiencelabs.onethought.presenter.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xperiencelabs.onethought.domain.use_cases.authentication.Authentication
import com.xperiencelabs.onethought.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authentication:Authentication
) :ViewModel(){
    val isUserAuthenticated
    get() = authentication.UserAuthenticationState()

    //signIn
    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState:State<Response<Boolean>> = _signInState

    //signUp
    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState:State<Response<Boolean>> = _signUpState

    //signOut
    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState:State<Response<Boolean>> = _signOutState

    private val _authState =  mutableStateOf<Boolean>(false)
    val authState:State<Boolean> = _authState


    fun signIn(email:String,password:String){
        viewModelScope.launch {
            authentication.SignIn(email,password).collect{
                _signInState.value = it
            }
        }
    }
    fun signUp(email: String,password: String,name:String){
        viewModelScope.launch {
            authentication.SignUp(email,password,name).collect{
                _signUpState.value = it
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            authentication.SignOut().collect{
                _signOutState.value = it
                //changing signIn state
                if (it==Response.Success(true)){
                    _signInState.value = Response.Success(false)
                }
            }
        }
    }

    fun getFireBaseAuthState(){
        viewModelScope.launch {
            authentication.FirebaseAuthState().collect{
                _authState.value = it
            }
        }

    }
}