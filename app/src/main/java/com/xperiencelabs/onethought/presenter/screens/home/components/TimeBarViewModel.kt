package com.xperiencelabs.onethought.presenter.screens.home.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeBarViewModel:ViewModel() {
    private val _timeLeft = mutableStateOf<Float>(0f)
    val timeLeft: State<Float> = _timeLeft
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
                val percentLeft =  percentCal(post,currentHour,totalTime)
                Log.e("Fuck post","post:- ${post.toString()} current:- $currentHour ")
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
            (100f-(remainingTime.toFloat() / validPeriod) * 100)
        }
    }
}