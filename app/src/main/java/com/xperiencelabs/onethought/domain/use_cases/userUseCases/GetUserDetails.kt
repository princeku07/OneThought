package com.xperiencelabs.onethought.domain.use_cases.userUseCases

import com.xperiencelabs.onethought.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(handleId:String) = userRepository.getUserDetails(handleId)
}