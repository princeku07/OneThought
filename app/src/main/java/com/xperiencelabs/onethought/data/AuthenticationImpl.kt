package com.xperiencelabs.onethought.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xperiencelabs.onethought.domain.model.User
import com.xperiencelabs.onethought.domain.repository.authentication.Authentication
import com.xperiencelabs.onethought.utils.Constants
import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthenticationImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
):Authentication {
    var signedInState:Boolean = false
    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser!=null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow{
            val authStateListner = FirebaseAuth.AuthStateListener {
                    trySend(
                        auth.currentUser == null
                    )
            }
        auth.addAuthStateListener(authStateListner)
        //awaitClose suspends the current coroutine until the channel is either closed or cancelled
        awaitClose{
            auth.removeAuthStateListener(authStateListner)
        }
    }

    override fun signUp(
        email: String,
        password: String,
        name: String
    ): Flow<Response<Boolean>> = flow{
        //if there is no user signed in then you can signUp
                signedInState = false
        try {
                emit(Response.Loading())
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    signedInState = true
                }.await()
            //getting user id
            if(signedInState){
                   val handleId = auth.currentUser?.uid!!
                   val obj = User(name = name, handleId = handleId, email = email, password = password)
                   fireStore.collection(Constants.USER_NAMES).document(handleId).set(obj).addOnSuccessListener {

                   }
                emit(Response.Success(signedInState))
            }
            else{
                Response.Success(signedInState)
            }
        }
        catch (e:Exception){
            emit(Response.Error(e.localizedMessage?:"Error"))
        }


    }

    override fun signIn(email: String, password: String): Flow<Response<Boolean>>  = flow{
        //check if user is already signed in
        signedInState = false
        try {
            emit(Response.Loading())
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                signedInState = true
            }.await()
            emit(Response.Success(signedInState))
        }
        catch (e:Exception){
            emit(Response.Error(e.localizedMessage?:"Error"))
        }
    }

    override fun signOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading())
            auth.signOut()
            emit(Response.Success(true))

        }
        catch(e:Exception){
            emit(Response.Error(e.localizedMessage?:"Error"))
        }
    }

}