package com.xperiencelabs.onethought.domain.use_cases.postUseCases

import com.xperiencelabs.onethought.domain.repository.post.PostRepository
import java.util.Date
import javax.inject.Inject

class UploadPost @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(
        postId:String,
        text: String,
        image: String,
        timeStamp: Date,
        like: Int,
        userId: String,
        userName: String,
        userProfile: String
    ) = repository.uploadPost(
        postId= postId,
        text=text,
        image=image,
        timeStamp=timeStamp,
        like=like,
        userId=userId,
        userName=userName,
        userProfile=userProfile
    )
}