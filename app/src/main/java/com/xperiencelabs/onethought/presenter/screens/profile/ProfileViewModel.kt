package com.xperiencelabs.onethought.presenter.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.xperiencelabs.onethought.domain.model.User
import com.xperiencelabs.onethought.domain.use_cases.authentication.Authentication
import com.xperiencelabs.onethought.domain.use_cases.userUseCases.UserUseCases
import com.xperiencelabs.onethought.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth:FirebaseAuth,
    private val userUseCases: UserUseCases,
    private val authentication: Authentication
):ViewModel(){

    private val handleId = auth.currentUser?.uid
    private val _userProfile = mutableStateOf(UserProfileState())
    val userProfile: State<UserProfileState> = _userProfile

    private val _setUserName = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setUserName:State<Response<Boolean>> = _setUserName


    fun userProfileInfo(){
        if (handleId!=null){
            viewModelScope.launch {
                userUseCases.GetUserDetail(handleId).collect{
                        when(it){
                            is Response.Success -> {
                                _userProfile.value = UserProfileState(userProfile = it.data)
                            }
                            is Response.Error -> {
                                _userProfile.value = UserProfileState(error = it.message)
                            }
                            is Response.Loading -> {
                                _userProfile.value = UserProfileState(isLoading = true)
                            }
                        }
                }
            }
        }
    }
    fun setUserName(name:String){
                    if (handleId != null){
                        viewModelScope.launch {
                            userUseCases.EditName(handleId = handleId, name = name).collect{
                                _setUserName.value = it
                            }
                        }
                    }
    }
    fun signOut(){

    }
}