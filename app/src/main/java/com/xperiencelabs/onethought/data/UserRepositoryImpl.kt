package com.xperiencelabs.onethought.data

import com.google.firebase.firestore.FirebaseFirestore
import com.xperiencelabs.onethought.domain.model.User
import com.xperiencelabs.onethought.domain.repository.user.UserRepository
import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore
):UserRepository {
    private var NameUpdateStatus = false
    private var AboutUpdateStatus = false
    private var profileUpdateStatus = false
    override fun getUserDetails(handleId:String): Flow<Response<User>> = callbackFlow{
//        Response.Loading()
        val snapShotListener = firebaseFireStore.collection("users")
            .document(handleId)
            .addSnapshotListener{snapshot,error->
                val response = if(snapshot!=null){
                    val userInfo = snapshot.toObject(User::class.java)
                    Response.Success<User>(userInfo!!)
                }
                else{
                    Response.Error(error?.message?:error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose{
            snapShotListener.remove()
        }

    }

    override fun editUserName(
        handleId: String,
        name: String
    ):Flow<Response<Boolean>> = flow{
        NameUpdateStatus = false
         try {
                val userObj = mutableMapOf<String,String>()
                userObj["name"] = name
                firebaseFireStore.collection("users").document(handleId).update(userObj as Map<String,Any>)
                    .addOnSuccessListener {

                    }.await()
             if(NameUpdateStatus){
                 emit(Response.Success(NameUpdateStatus))
             }
             else {
                 emit(Response.Error("Can't edit"))
             }
         }catch (e:Exception){
          emit(  Response.Error(e.localizedMessage?:"Error"))
         }
    }

    override fun editUserAbout(handleId: String, about: String): Flow<Response<Boolean>> = flow {
        AboutUpdateStatus = false
        try {
            val userObj = mutableMapOf<String,String>()
            userObj["about"] = about
            firebaseFireStore.collection("users").document(handleId).update(userObj as Map<String,Any>)
                .addOnSuccessListener {

                }.await()
            if(AboutUpdateStatus){
                emit(Response.Success(AboutUpdateStatus))
            }
            else {
                emit(Response.Error("Can't edit"))
            }
        }catch (e:Exception){
            emit(Response.Error(e.localizedMessage?:"Error"))
        }
    }

    override fun editProfile(handleId: String, profile: String):Flow<Response<Boolean>> = flow {
        profileUpdateStatus= false
        try {
            val userObj = mutableMapOf<String,String>()
            userObj["profile"] = profile
            firebaseFireStore.collection("users").document(handleId).update(userObj as Map<String,Any>)
                .addOnSuccessListener {

                }.await()
            if(profileUpdateStatus){
                emit(Response.Success(profileUpdateStatus))
            }
            else {
                emit(Response.Error("can't update profile"))
            }

        }catch (e:Exception){
            emit(Response.Error(e.localizedMessage?:"Error"))
        }
    }
}