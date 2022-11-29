package com.xperiencelabs.onethought.domain.use_cases.authentication

import com.xperiencelabs.onethought.domain.repository.authentication.Authentication
import javax.inject.Inject

class SignUp @Inject constructor(
    private val repository: Authentication
) {
    operator fun invoke(email:String,password:String,name:String) = repository.signUp(email,password,name)
}