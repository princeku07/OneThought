package com.xperiencelabs.onethought.domain.repository.user

import com.xperiencelabs.onethought.domain.model.User
import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserDetails(handleId:String): Flow<Response<User>>
    fun editUserName(
        handleId: String,
        name:String
    ):Flow<Response<Boolean>>
    fun editUserAbout(
        handleId: String,
        about: String
    ):Flow<Response<Boolean>>

    fun editProfile(
        handleId:String,
        profile:String
    ):Flow<Response<Boolean>>

}