package com.xperiencelabs.onethought.domain.use_cases.postUseCases

import android.net.Uri
import com.xperiencelabs.onethought.domain.repository.post.PostRepository
import javax.inject.Inject

class UploadImage @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(imageUri: Uri,postId:String) = postRepository.addImageToFirebase(imageUri,postId)
}