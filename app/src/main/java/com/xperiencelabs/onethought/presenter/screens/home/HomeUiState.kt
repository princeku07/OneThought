package com.xperiencelabs.onethought.presenter.screens.home

import com.xperiencelabs.onethought.domain.model.Post

data class HomeUiState(
    val isLoading:Boolean = false,
    val posts:List<Post> = emptyList(),
    val error:String = ""

)
