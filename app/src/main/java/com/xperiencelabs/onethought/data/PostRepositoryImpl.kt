package com.xperiencelabs.onethought.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.xperiencelabs.onethought.domain.model.Post
import com.xperiencelabs.onethought.domain.model.User
import com.xperiencelabs.onethought.domain.repository.post.PostRepository
import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestoreCollection: CollectionReference,
    private val storage: FirebaseStorage
) :PostRepository {
    private var postUploadStatus: Boolean = false


    override fun getAllPosts(userId: String): Flow<Response<List<Post>>> = callbackFlow {
//        Response.Loading()
        val snapshotListner = firestoreCollection
//            .whereNotEqualTo("userId",userId)
            .addSnapshotListener { snapshot, error ->
                val response = if (snapshot != null) {
                    //transfer post data and converting it to post model created
                    val postList = snapshot.toObjects(Post::class.java)
                    Response.Success<List<Post>>(postList)
                } else {
                    Response.Error(error?.message ?: error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListner.remove()
        }
    }

    override fun uploadPost(
        postId: String,
        text: String,
        image: String,
        timeStamp: Date,
        like: Int,
        userId: String,
        userName: String,
        userProfile: String,
    ): Flow<Response<Boolean>> = flow {
        postUploadStatus = false
        try {
//            val postId = firestoreCollection.document().id
            val post = Post(
                postId = postId,
                text = text,
                image = image,
                timeStamp = timeStamp,
                like = like,
                userId = userId,
                userName = userName,
                userProfile = userProfile
            )
            firestoreCollection.document(postId).set(post)
                .addOnSuccessListener {
                    postUploadStatus = true
                }.await() //will not execute further until task completed
            if (postUploadStatus) {
                emit(Response.Success(postUploadStatus))
            } else {
                emit(Response.Error("Error uploading post"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "something went wrong"))
        }


    }

    override fun addImageToFirebase(imageUri: Uri,postId:String) = flow{
        try {
            emit(Response.Loading())
            val downloadUrl = storage.getReferenceFromUrl("gs://onethoughtapp.appspot.com/")
                .child("$postId.jpg")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()

//              Log.e("Fuck hard repo","$downloadUrl")
            emit(Response.Success(downloadUrl))
        }catch (e:Exception){
            emit(Response.Error(e.localizedMessage?:"image upload failed"))
        }
    }

}