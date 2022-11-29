package com.xperiencelabs.onethought.presenter.icons
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.xperiencelabs.onethought.R

object Icons {
    val AccountCircle = Icons.Outlined.AccountCircle
    const val OutlinedHome = R.drawable.ic_outline_home_24
    const val FilledHome = R.drawable.ic_baseline_home_24
    const val OutlinedSearch = R.drawable.ic_outline_search_24
    const val FilledSearch = R.drawable.ic_baseline_search_24
    const val OutlinedLike = R.drawable.ic_outline_favorite_border_24
    const val FilledLike = R.drawable.ic_baseline_favorite_24
    const val OutlinedPerson = R.drawable.ic_outline_person_outline_24
    const val FilledPerson = R.drawable.ic_baseline_person_24
    const val Notification = R.drawable.ic_outline_notifications_24

}

sealed class Icon{
    data class ImageVectorIcon(val imageVector:ImageVector) :Icon()
    data class DrawableResourceIcon(@DrawableRes val id:Int) :Icon()
}