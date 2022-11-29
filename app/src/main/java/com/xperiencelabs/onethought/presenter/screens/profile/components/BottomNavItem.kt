package com.xperiencelabs.onethought.presenter.screens.profile.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavButton(
    val purpose:String,
    val route:String,
    val SelectedIcon :Int,
    val UnSelectedIcon:Int,
    val badgeCount:Int =0
)
