package com.xperiencelabs.onethought.presenter.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    content:@Composable RowScope.() -> Unit
) {
      NavigationBar(
          modifier = Modifier,
          contentColor = Color.Cyan,
          tonalElevation = 0.dp,
          content = content
      )
}

@Composable
fun RowScope.AppNavigationBarItem(
    selected:Boolean,
    onClick:()->Unit,
    modifier: Modifier = Modifier,
    icon:@Composable () -> Unit,
    selectedIcon:@Composable ()->Unit = icon,
    enabled:Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel:Boolean = true

) {
    NavigationBarItem(selected = selected, onClick = onClick,
        icon = if(selected) selectedIcon else icon,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        modifier = modifier,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black ,
            unselectedIconColor = Color.LightGray,
            selectedTextColor = Color.Black,
            unselectedTextColor = Color.LightGray,
            indicatorColor = Color.Red
        )
        )
}