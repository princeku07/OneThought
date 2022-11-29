package com.xperiencelabs.onethought.presenter.screens.home

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.xperiencelabs.onethought.domain.model.Post
import com.xperiencelabs.onethought.domain.use_cases.postUseCases.PostUseCases
import com.xperiencelabs.onethought.presenter.utils.Toast
import com.xperiencelabs.onethought.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val auth:FirebaseAuth,
    firestoreCollection: CollectionReference,

    ):ViewModel() {

    private val _posts = mutableStateOf(HomeUiState())
    val posts: State<HomeUiState> =_posts

    val postId = firestoreCollection.document().id
    private val _uploadPost = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val uploadPost:State<Response<Boolean>> = _uploadPost

    private val _addImage = mutableStateOf<Response<Uri>>(Response.Success(null))
    val uploadImage:State<Response<Uri>> = _addImage

    private val _timeLeft = mutableStateOf<Float>(0f)
    val timeLeft:State<Float> = _timeLeft

    fun getAllPost(){
        val handleId = auth.currentUser?.uid!!
        viewModelScope.launch {
           postUseCases.getAllPosts(handleId).collect{
               when(it){

                   is Response.Success ->
                   {
                       _posts.value = HomeUiState(posts = it.data ?: emptyList())
                   }
                   is Response.Loading -> {
                       _posts.value = HomeUiState(isLoading = true)
                   }
                   is Response.Error ->{
                       _posts.value = HomeUiState(error = it.message?:"Error")
                   }
               }
           }
        }
    }

    fun uploadImageToFirebase(imageUri:Uri,postId:String){
        viewModelScope.launch {
            _addImage.value = Response.Loading()
            postUseCases.uploadImage(
                imageUri=imageUri,
                postId
            ).collect{
                when (it){
                   is Response.Success ->{

                       _addImage.value = it
                   }

                    is Response.Loading -> {

                    }
                    is Response.Error -> {

                    }
                }


            }
        }
    }

    fun uploadThought(
        text: String,
        image: String,
        timeStamp: Date,
        like: Int,
        userId: String,
        userName: String,
        userProfile: String
    ){
        viewModelScope.launch {

            postUseCases.uploadPost(
                postId= postId,
                text=text,
                image=image,
                timeStamp=timeStamp,
                like=like,
                userId=userId,
                userName=userName,
                userProfile=userProfile
            ).collect{
                _uploadPost.value = it
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun progressTime(postTime: Date){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
//                val minute = DateTimeFormatter.ofPattern("mm")
                val hour = DateTimeFormatter.ofPattern("HH")
//                val currentMinutes = LocalDateTime.now().format(minute).toInt()
                val currentHour = LocalDateTime.now().format(hour).toInt()
                val formattedDate =  SimpleDateFormat("HH")
                val post = formattedDate.format(postTime).toInt()
                val totalTime = 24
                val percentLeft =  percentCal(14,currentHour,totalTime)
                Log.e("Fuck post","post:- ${post.toString()} current:- $currentHour percent:- $percentLeft ")
                while(true){
                    if (percentLeft < 100){
                        withContext(Dispatchers.Main){
                            _timeLeft.value =  percentLeft
                        }
                    }else {
                        withContext(Dispatchers.Main){
                            _timeLeft.value = 100f
                        }
                        break
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun percentCal(postTime:Int, currentTime:Int, validPeriod:Int):Float{
        val targetTime = postTime + validPeriod
        val remainingTime = targetTime - currentTime
        val timeCompleted = 100f
        return if(remainingTime==0){
            timeCompleted
        } else {
            (remainingTime.toFloat() / validPeriod) * 100
        }
    }


}