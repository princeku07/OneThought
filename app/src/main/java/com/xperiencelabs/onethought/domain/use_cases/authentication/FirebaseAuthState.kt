package com.xperiencelabs.onethought.domain.use_cases.authentication

import com.xperiencelabs.onethought.domain.repository.authentication.Authentication
import javax.inject.Inject

class FirebaseAuthState @Inject constructor(
    private val repository: Authentication
) {
    operator fun invoke() = repository.getFirebaseAuthState()
}