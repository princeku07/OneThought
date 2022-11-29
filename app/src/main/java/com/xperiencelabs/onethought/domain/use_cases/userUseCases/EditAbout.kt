package com.xperiencelabs.onethought.domain.use_cases.userUseCases

import com.xperiencelabs.onethought.domain.repository.user.UserRepository
import javax.inject.Inject

class EditAbout @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(handleId:String,about:String) = userRepository.editUserAbout(handleId = handleId , about = about)
}