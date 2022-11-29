package com.xperiencelabs.onethought.presenter.screens.home.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.xperiencelabs.onethought.presenter.screens.home.HomeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.xperiencelabs.onethought.presenter.utils.Toast
import com.xperiencelabs.onethought.utils.Response


@Composable
fun UploadImage(

) {

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }



    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {
               imageUri = it
        }
    }

    Column() {
        Button(onClick = {
            galleryLauncher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(400.dp))
            }
        }

    }
}










//                    when (val response = viewModel.uploadImage.value) {
//
//                        is Response.Loading -> {
//                            CircularProgressIndicator(
//                                modifier = Modifier.fillMaxSize())
//                        }
//                        is Response.Success -> {
//                            if (response.data != null) {
//                                LaunchedEffect(key1 = true) {
//                                    image = response.data.toString()
//                                    buttonVisibility = !buttonVisibility
//                                }
//                            }
//                        }
//                        is Response.Error -> {
//                            Toast(message = response.message ?: "error uploading image")
//                        }
//                    }