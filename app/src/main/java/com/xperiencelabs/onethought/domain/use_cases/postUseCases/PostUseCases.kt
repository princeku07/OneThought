package com.xperiencelabs.onethought.domain.use_cases.postUseCases

data class PostUseCases (
    var getAllPosts: GetAllPosts,
    var uploadPost: UploadPost,
    var uploadImage:UploadImage
        )