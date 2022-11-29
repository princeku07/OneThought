package com.xperiencelabs.onethought.domain.model

import java.util.Date

data class Post(
    var postId:String="",
    var text:String="",
    var image:String="",
    var timeStamp:Date?=null,
    var like:Int =0,
    var userId:String ="",
    var userName:String = "",
    var userProfile:String ="",

)
