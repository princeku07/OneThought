package com.xperiencelabs.onethought.domain.model


data class User (
    var handleId :String = "",
    var name: String = "",
    var profile :String = "",
    var about:String = "",
    var email:String = "",
    var password:String = "",
    var following:List<String> = emptyList(),
    var followers:List<String> = emptyList()
)