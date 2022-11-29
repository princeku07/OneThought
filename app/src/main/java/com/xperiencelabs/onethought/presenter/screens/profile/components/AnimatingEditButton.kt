package com.xperiencelabs.onethought.presenter.screens.profile.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt

@Composable
fun AnimatingEditButton(
    icon:@Composable ()-> Unit,
    text:@Composable () -> Unit,
    modifier: Modifier = Modifier,
    extended:Boolean = true
) {
    val currentState = if(extended) ExpandableButtonStates.Extended else ExpandableButtonStates.Collapsed
    val transition = updateTransition(currentState,"button_transition" )
    val textOpacity by transition.animateFloat(
        transitionSpec =  {
            if(targetState == ExpandableButtonStates.Collapsed){
                tween(
                    easing = LinearEasing,
                    durationMillis = (transitionDuration / 12f *5).roundToInt()
                )
            } else {
                tween(
                    easing = LinearEasing,
                    delayMillis = (transitionDuration /3f).roundToInt(),
                    durationMillis = (transitionDuration / 12f *5).roundToInt()
                )
            }
        },
        label = "button_text_opacity"
    ) {state ->
      if(state == ExpandableButtonStates.Collapsed) {
          0f
      }else{
          1f
      }

    }
    val ButtonWidthFactor by transition.animateFloat(
        transitionSpec = {
            if (targetState == ExpandableButtonStates.Collapsed) {
                tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = transitionDuration
                )
            } else {
                tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = transitionDuration
                )
            }
        },
        label = "fab_width_factor"
    ) { state ->
        if (state == ExpandableButtonStates.Collapsed) {
            0f
        } else {
            1f
        }
    }
    IconAndTextRow(
        icon = icon,
        text = text,
        {textOpacity},
        {ButtonWidthFactor},
        modifier = modifier
    )
}

@Composable
fun IconAndTextRow(
    icon:@Composable () -> Unit,
    text:@Composable () -> Unit,
    opacityProgress: () -> Float,
    widthProgress: () -> Float,
    modifier: Modifier
) {
    Layout( modifier = modifier,
        content = {
                 icon()
                  Box(modifier = Modifier.graphicsLayer { alpha = opacityProgress() }){
                      text()
                  }
    }
    ){  measurables, constraints ->
        val iconPlaceable = measurables[0].measure(constraints)
        val textPlaceable = measurables[1].measure(constraints)
        val height = constraints.maxHeight
        
        val initialWidth = height.toFloat()
        val iconPadding = (initialWidth - iconPlaceable.width) /2f
        val expandedWidth = iconPlaceable.width + textPlaceable.width + iconPadding*3
        val width = lerp(initialWidth,expandedWidth,widthProgress())
        layout(width.roundToInt(),height){
            iconPlaceable.place(
                iconPadding.roundToInt(),
                constraints.maxHeight/2- iconPlaceable.height/2
            )
        }
    }
}

private enum class ExpandableButtonStates { Collapsed, Extended}
private const val transitionDuration = 200