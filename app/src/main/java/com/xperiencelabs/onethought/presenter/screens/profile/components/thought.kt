package com.xperiencelabs.onethought.presenter.screens.profile.components

data class Thought(
    val profileImage:String,
    val name:String,
    val text:String,
    val image:String? = null,
    val timeStamp:String,
    val Likes:Int,
)
