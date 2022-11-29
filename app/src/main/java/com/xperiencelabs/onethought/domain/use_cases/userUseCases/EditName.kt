package com.xperiencelabs.onethought.domain.use_cases.userUseCases

import com.xperiencelabs.onethought.domain.repository.user.UserRepository
import javax.inject.Inject

class EditName @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(handleId:String,name:String) = userRepository.editUserName(handleId = handleId, name = name)
}