package com.xperiencelabs.onethought.presenter.screens.profile

import com.xperiencelabs.onethought.domain.model.User

data class UserProfileState(
    val isLoading:Boolean = false,
    val userProfile:User? = null,
    val error:String? = ""
        )