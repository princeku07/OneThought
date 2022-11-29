package com.xperiencelabs.onethought.presenter.screens.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    items:List<NavButton>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick:(NavButton) -> Unit
) {
    val backStack = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
            .border(1.dp,Color.LightGray, shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)

        ),
        backgroundColor = Color(0xFFFFFFFF),
        elevation = 10.dp
    ) {
        items.forEach{
            val selected = it.route == backStack.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(it) },
                selectedContentColor = Color(0xFF000000),
                unselectedContentColor = Color(0xFF818181),
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        if(it.badgeCount > 0){
                            BadgedBox(badge = {
                                Text(text = it.badgeCount.toString(), fontSize = 10.sp, color = Color.Red)
                            }) {
//                                Icon(imageVector = it.icon, contentDescription = it.purpose)
                                Icon(painter = painterResource(it.SelectedIcon) , contentDescription = it.purpose)
                            }
                        }else {
                            if (selected) Icon(painter = painterResource(it.SelectedIcon), contentDescription = it.purpose )
                            else Icon(painter = painterResource(it.UnSelectedIcon), contentDescription = it.purpose)
                        }
                        if (selected){
                            Text(text = it.purpose,
                                textAlign = TextAlign.Center,
                                color=Color.Black,
                                fontSize = 10.sp)
                        }
                    }
                }
            )

        }
    }
}

