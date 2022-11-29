package com.xperiencelabs.onethought.presenter.navigation

import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.presenter.icons.Icon
import com.xperiencelabs.onethought.presenter.icons.Icons

enum class TopLevelDestination (
    val selectedIcon: Icon,
    val unselectedIcon:Icon,
    val iconTextId:Int,
    val titleTextId:Int
        ){
    HOME(
        selectedIcon = Icon.DrawableResourceIcon(Icons.FilledHome),
        unselectedIcon = Icon.DrawableResourceIcon(Icons.OutlinedHome),
        iconTextId = R.string.Home,
        titleTextId = R.string.Home
    ),
    SEARCH(
    selectedIcon = Icon.DrawableResourceIcon(Icons.FilledSearch),
    unselectedIcon = Icon.DrawableResourceIcon(Icons.OutlinedSearch),
    iconTextId = R.string.Home,
    titleTextId = R.string.Home
    ),
    LIKE(
    selectedIcon = Icon.DrawableResourceIcon(Icons.FilledLike),
    unselectedIcon = Icon.DrawableResourceIcon(Icons.OutlinedLike),
    iconTextId = R.string.Home,
    titleTextId = R.string.Home
    ),
    ME(
        selectedIcon = Icon.DrawableResourceIcon(Icons.FilledPerson),
        unselectedIcon = Icon.DrawableResourceIcon(Icons.OutlinedPerson),
        iconTextId = R.string.Home,
        titleTextId = R.string.Home
    )
}