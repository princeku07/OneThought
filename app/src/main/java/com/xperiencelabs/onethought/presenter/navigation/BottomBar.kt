package com.xperiencelabs.onethought.presenter.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.xperiencelabs.onethought.presenter.icons.Icon

@Composable
fun BottomBar(
    destination: List<TopLevelDestination>,
    onNavigateToDestination:(TopLevelDestination) -> Unit,
    currentDestination:NavDestination?
) {
       AppNavigationBar {
           destination.forEach{ destination->
               val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
               AppNavigationBarItem(selected = selected,
                   onClick = {onNavigateToDestination(destination)},
                   icon = {
                       val icon = if (selected){
                           destination.selectedIcon
                       }else destination.unselectedIcon
                       when (icon) {
                           is Icon.ImageVectorIcon -> Icon(
                               imageVector = icon.imageVector,
                               contentDescription = null
                           )

                           is Icon.DrawableResourceIcon -> Icon(
                               painter = painterResource(id = icon.id),
                               contentDescription = null
                           )
                       }
                   },
                   label = {Text(stringResource(destination.iconTextId))}
               )
           }
       }
}
private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false