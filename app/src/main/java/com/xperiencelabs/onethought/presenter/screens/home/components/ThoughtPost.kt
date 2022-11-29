package com.xperiencelabs.onethought.presenter.screens.home.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.domain.model.Post
import com.xperiencelabs.onethought.presenter.screens.home.HomeViewModel
import com.xperiencelabs.onethought.presenter.screens.profile.components.RoundImage
import com.xperiencelabs.onethought.ui.theme.Description
import com.xperiencelabs.onethought.ui.theme.interactionBg
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun ThoughtPost(
    post: Post,
    viewModel: HomeViewModel

) {
    var liked by rememberSaveable {
        mutableStateOf(false)
    }
    val formattedDate =  SimpleDateFormat("dd.MM.yyyy  'at' HH:mm ")
    val postDateTime = post.timeStamp?.let { date -> formattedDate.format(date) }
    Row(modifier = Modifier.padding(start = 10.dp,end=10.dp, bottom = 30.dp, top = 20.dp)) {
        Column(
            modifier = Modifier.size(height = 400.dp, width = 50.dp)
        ){
            RoundImage(image = post.userProfile,modifier = Modifier.size(40.dp))
            post.timeStamp?.let { TimeBar(viewModel = viewModel, postTime = it) }

        }

        Column {

         Row(horizontalArrangement = Arrangement.SpaceBetween,
             verticalAlignment = Alignment.CenterVertically) {
             Text(text = post.userName)
             if (postDateTime != null){
                 Text(text = postDateTime, color = Color.LightGray, fontSize = 10.sp, modifier = Modifier.padding(start = 100.dp))

             }
         }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Column {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Description)
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 20.dp)
                    ) {

                        GlideImage(imageModel = { post.image }, modifier = Modifier.size(500.dp,300.dp))
                        Text(text = post.text, modifier = Modifier.padding(8.dp))
                    }
                    Row(modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(interactionBg),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End) {
                        Icon(painter = painterResource(id = if (liked) R.drawable.lit_bulb else R.drawable.bulb), tint = Color.Yellow,
                            contentDescription = null, modifier = Modifier
                                .padding(5.dp)
                                .size(30.dp)
                                .clickable(onClick = {
                                    liked = !liked
                                    if (liked) {
                                        post.like++
                                    } else if (!liked && post.like > 0) {
                                        post.like--
                                    }
                                }))
                        Text(text = " ${post.like} Likes",color=Color.White, modifier = Modifier
                            .padding(5.dp,5.dp,10.dp,5.dp))
                    }


                }


            }
        }

    }

}