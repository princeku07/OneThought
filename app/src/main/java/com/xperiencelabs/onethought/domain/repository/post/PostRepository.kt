package com.xperiencelabs.onethought.domain.repository.post

import android.net.Uri
import com.xperiencelabs.onethought.domain.model.Post
import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.*

interface PostRepository {

    fun getAllPosts(userId:String): Flow<Response<List<Post>>>

    fun uploadPost(
        postId: String,
        text:String,
        image:String,
        timeStamp: Date,
        like:Int =0,
        userId:String,
        userName:String,
        userProfile:String
    ):Flow<Response<Boolean>>

    fun addImageToFirebase(imageUri:Uri,postId:String):Flow<Response<Uri>>

}