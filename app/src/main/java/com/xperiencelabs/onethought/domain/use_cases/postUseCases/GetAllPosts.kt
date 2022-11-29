package com.xperiencelabs.onethought.domain.use_cases.postUseCases

import com.xperiencelabs.onethought.domain.repository.post.PostRepository
import javax.inject.Inject

class GetAllPosts @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(userId:String) = repository.getAllPosts(userId)
}