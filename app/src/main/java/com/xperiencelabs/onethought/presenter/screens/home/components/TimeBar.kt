package com.xperiencelabs.onethought.presenter.screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xperiencelabs.onethought.presenter.screens.home.HomeViewModel
import com.xperiencelabs.onethought.ui.theme.ButtonBg
import com.xperiencelabs.onethought.ui.theme.bar
import com.xperiencelabs.onethought.ui.theme.barBg
import java.util.Date

@Composable
fun TimeBar(
    viewModel: HomeViewModel,
    animationDuration: Int = 3000,
    animationDelay: Int = 1,
    postTime:Date
) {
    val timePercentage = viewModel.timeLeft.value
    val animateNumber = animateFloatAsState(
        targetValue = timePercentage,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )
    LaunchedEffect(key1 = true){
        viewModel.progressTime(postTime)
    }
    Box(
        contentAlignment = Alignment.Center
    ){
        Canvas(modifier = Modifier
            .fillMaxHeight()
            .padding(20.dp)
        ){

            drawLine(
                start = Offset(x=0f,y=size.height),
                end = Offset(x=0f,y=0f),
                cap=StrokeCap.Butt,
                color = bar,
                strokeWidth = 15f
            )
            val progress = (animateNumber.value/100) * size.height
            drawLine(
                start = Offset(x=0f,y=progress),
                end = Offset(x=0f,y=0f),
                cap=StrokeCap.Butt,
                color = barBg,
                strokeWidth = 15f
            )
        }
    }


}