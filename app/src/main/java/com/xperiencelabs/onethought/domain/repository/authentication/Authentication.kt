package com.xperiencelabs.onethought.domain.repository.authentication

import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.flow.Flow


interface Authentication {

    fun isUserAuthenticated():Boolean
    //provides current state of user in application
    fun getFirebaseAuthState():Flow<Boolean>

    fun signUp(email:String,password: String,name:String):Flow<Response<Boolean>>
    fun signIn(email:String,password:String):Flow<Response<Boolean>>
    fun signOut():Flow<Response<Boolean>>

}