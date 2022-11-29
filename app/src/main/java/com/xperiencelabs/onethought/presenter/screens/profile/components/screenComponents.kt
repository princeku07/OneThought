package com.xperiencelabs.onethought.presenter.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.domain.model.User
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.ui.theme.Description
import com.xperiencelabs.onethought.ui.theme.Link

@Composable
fun Notification(
    navButton: NavButton,
) {
    Row(

    ) {
        BadgedBox(badge = {
//            Text(text = navButton.badgeCount.toString(), fontSize = 10.sp, color = Color.Red)
            Icon(painter = painterResource(id = R.drawable.ic_baseline_circle_24 ), tint = Color.Red, contentDescription = null,
                modifier = Modifier.size(10.dp))
        }) {
//                                Icon(imageVector = it.icon, contentDescription = it.purpose)
            Icon(painter = painterResource(navButton.SelectedIcon) , contentDescription = navButton.purpose)
        }
    }

}


@Composable
fun ProfileSection(
    user: User,
    navController: NavController,
    nestedScroll: NestedScrollConnection = rememberNestedScrollInteropConnection(),
    userIsMe:Boolean
) {

    val scrollState = rememberScrollState()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScroll)
            .systemBarsPadding()
            .padding(bottom = 50.dp)
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val link ="https://www.holidify.com/images/bgImages/UDAIPUR.jpg"


                RoundImage(image = user.profile, modifier = Modifier
                    .size(100.dp))
                Spacer(modifier = Modifier.height(16.dp))
                StatSection(user = user)
                Spacer(modifier = Modifier.height(20.dp))
                ProfileDescription(name = user.name, about = "I am software developer currently working on android" ,
                    Url = "www.Experience.co.in", followedBy = listOf("beff zejos","zark suckerberg"), otherCount = 13)
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(modifier = Modifier.height(2000.dp))
                Thoughts()



            }


        }
        val buttonExtended by remember {
            derivedStateOf { scrollState.value == 0 }
        }
        EditButton(
            extended = buttonExtended,
            // who visits profile if user itself visit profile than he should be able to edit profile
            userIsMe = userIsMe,
            modifier = Modifier.align(Alignment.BottomEnd)
                .offset(y = ((-10).dp)),
            onEditClicked = {
                if(userIsMe){
                    navController.navigate(ScreenRoutes.EditProfileScreen.route){
                        popUpTo(ScreenRoutes.EditProfileScreen.route){
                            inclusive = true
                        }
                    }
                }

            }
        )
    }
}

@Composable
fun EditButton(
    extended:Boolean,
    userIsMe:Boolean,
    modifier:Modifier = Modifier,
    onEditClicked:()-> Unit
) {
    key(userIsMe) {
        FloatingActionButton(
            onClick = onEditClicked,
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            backgroundColor = Description

        ) {
               AnimatingEditButton(icon = {
                   Icon(imageVector =  if(userIsMe) Icons.Outlined.Create else Icons.Outlined.Person,
                       contentDescription = if(userIsMe) "Edit Profile" else "Message")
               },
                   text = {
                       Text(text = if(userIsMe)"Edit Profile" else "Message", color = Color.Black)
                   }, extended = extended
               )
        }
    }
}





@Composable
fun RoundImage(
    image:String,
    modifier: Modifier = Modifier
) {

    GlideImage(imageModel = { image },
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .size(35.dp)
            .padding(2.dp)
            .border(1.dp, Color.LightGray, CircleShape)
            .clip(CircleShape))
}

@Composable
fun StatSection(
    modifier: Modifier = Modifier,
    user: User
) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
        //user.followers.size.toString()
        ) {
            ProfileStat(number = "10" , text = "Posts", modifier = modifier )
               ProfileStat(number = "4.5K" , text = "Followers", modifier = modifier )
               ProfileStat(number = "26" , text = "Following", modifier = modifier )

        }
}

@Composable
fun ProfileStat(
     number:String,
     text:String,
     modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(start = 19.dp)
    ) {
             Text(text = number,
             fontWeight = FontWeight.Bold,
             fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
fun ProfileDescription(
    name:String,
    about:String,
    Url:String,
    followedBy:List<String>,
    otherCount:Int
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
           modifier = Modifier
               .clip(RoundedCornerShape(20.dp))
               .fillMaxWidth()
               .background(Description)
               .padding(horizontal = 20.dp, vertical = 8.dp)

    ) {
         Text(text = name,
              fontWeight = FontWeight.Bold,
             fontSize = 20.sp ,
             letterSpacing = letterSpacing,
             lineHeight = lineHeight
             )
        Text(text = about,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp ,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(text = Url,
            fontWeight = FontWeight.Normal,
            letterSpacing = letterSpacing,
            fontSize = 15.sp ,
            lineHeight = lineHeight,
            color = Link
        )
        if(followedBy.isNotEmpty()){
            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    append("Followed by ")

                    followedBy.forEachIndexed{ index,name ->
                        pushStyle(boldStyle)
                        append(name)
                        pop()
                        if(index < followedBy.size -1){
                            append(" , ")
                        }
                    }
                    if(otherCount > 2){
                        append(" and ")
                        pushStyle(boldStyle)
                        append("$otherCount others")
                    }
                },letterSpacing= letterSpacing,
                lineHeight = lineHeight,
                fontSize = 15.sp ,
            )
        }

    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        AppButton(text = "Edit Profile") {
            navController.navigate(ScreenRoutes.EditProfileScreen.route){
                popUpTo(ScreenRoutes.EditProfileScreen.route){
                    inclusive = true
                }
            }
        }

//         StateButton(
//             text = "Edit Profile",
//             icon = Icons.Default.KeyboardArrowDown,
//             modifier = Modifier
//                 .defaultMinSize(minWidth = minWidth)
//                 .height(height)
//         )
    }
}





@Composable
fun StateButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon:ImageVector? = null,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(6.dp)

    ) {
           text?.let {
               Text(text = it,
               fontWeight = FontWeight.SemiBold,
               fontSize = 14.sp)
           }
        icon?.let {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
        }

    }
}

@Composable
fun AppButton(
    modifier: Modifier= Modifier,
    text:String,
    onClick:() -> Unit
) {
    OutlinedButton(onClick = onClick) {
        Text(text =text, fontWeight = FontWeight.SemiBold, fontSize = 15.sp ,
        modifier = modifier

            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp))
    }
}

@Composable
fun NavTab(
    modifier: Modifier = Modifier,
    tabTexts: List<TabTexts>,
    onTabSelected:(selectedIndex:Int) -> Unit
) {
   var selectedTabIndex by remember {
       mutableStateOf(0)
   }
    val inactiveColor = Color.LightGray
    val activeColor = Color.Black

    TabRow(selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier)
    {
        tabTexts.forEachIndexed{ index,item ->
            Tab(selected = selectedTabIndex == index,
                selectedContentColor = activeColor,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }) {
                Text(text = item.text,
                    color = if(selectedTabIndex==index) Color.Black else inactiveColor,
                    modifier = Modifier.padding(10.dp)
                )
            }

        }

    }
}

@Composable
fun ThoughtsSection(

) {
    
}

@Composable
fun Thoughts(
//    thought: Thought,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),
    contentAlignment = Alignment.TopStart){
        RoundImage(image = "https://www.holidify.com/images/bgImages/UDAIPUR.jpg",modifier= modifier.size(40.dp))
        Text(text = "hi today I thought of this")
    }

}

