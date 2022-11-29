package com.xperiencelabs.onethought.presenter.screens.home



import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.xperiencelabs.onethought.R
import com.xperiencelabs.onethought.presenter.screens.profile.ProfileViewModel
import com.xperiencelabs.onethought.presenter.utils.ScreenRoutes
import com.xperiencelabs.onethought.presenter.utils.Toast
import com.xperiencelabs.onethought.ui.theme.ButtonBg
import com.xperiencelabs.onethought.ui.theme.Description
import com.xperiencelabs.onethought.utils.Response
import kotlinx.coroutines.delay
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun PostScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,

) {
    var buttonVisibility by remember {
        mutableStateOf(false)
    }
    val postId = viewModel.postId
    var profilePic by remember {
        mutableStateOf(profileViewModel.userProfile.value.userProfile?.profile)
    }
    val text = remember {
        mutableStateOf(TextFieldValue(""))
    }
    var image by remember {
        mutableStateOf("")
    }
    val date by remember {
        mutableStateOf(Date())

    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            imageUri = it
                buttonVisibility=!buttonVisibility

        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Description)
            .size(500.dp, 300.dp))
        {

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

            IconButton(onClick = {
                galleryLauncher.launch("image/*")
                 },
                modifier = Modifier
                    .align(Alignment.Center)
                    .border(1.dp, color = ButtonBg,
                        shape = RoundedCornerShape(15.dp)))
            {
                Text(text = "Select Image", fontSize = 15.sp,
                    color =  ButtonBg,
                    modifier = Modifier.padding(start=9.dp,end=9.dp))
            }

            Column(modifier = Modifier.align(Alignment.BottomEnd),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    imageUri?.let {viewModel.uploadImageToFirebase(it,postId)}

                },
                    enabled=buttonVisibility,
                    modifier = Modifier
                        .border(1.dp, color = if (buttonVisibility) ButtonBg else Color.LightGray ,
                            shape = CircleShape))
                {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_cloud_upload_24),
                        contentDescription = null,
                        tint = if(buttonVisibility) ButtonBg else Color.LightGray )



                    when(val response =  viewModel.uploadImage.value)   {
                        is Response.Loading ->{

                        }
                        is Response.Success ->{
                            if(response.data!=null){
                                LaunchedEffect(key1 = true ){
                                    image= response.data.toString()
                                    delay(1000)
                                }

                            }
                        }
                        is Response.Error -> {
                            response.message?.let {
                            }
                        }

                    }

                }
                Text(text = "Upload Image", fontSize = 10.sp,
                    color = if(buttonVisibility) ButtonBg else Color.LightGray ,
                    modifier = Modifier.padding( 3.dp))

            }


        }
        TextField(value = text.value,
            label = { Text(text = "Thought") },
            placeholder = { Text(text = "write your thought here..") },
            modifier = Modifier
                .padding(start = 12.dp),
            onValueChange = { text.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Description,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            shape = RoundedCornerShape(8.dp))



        IconButton(onClick = {
            imageUri?.let { viewModel.uploadImageToFirebase(it, postId) }
            viewModel.uploadThought(
                text = text.value.text,
                image = image,
                timeStamp = date,
                like = 0,
                userId = "@Nodi_zeffos",
                userName = "Marendra Nodi",
                userProfile = "https://gumlet.assettype.com/swarajya%2F2019-05%2F1ab5ff95-6463-48b8-ab58-2ecf581efe63%2Famit_shah.jpg"
            )
        },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 15.dp, top = 15.dp)
                .border(1.dp, color = ButtonBg,
                    shape = RoundedCornerShape(15.dp))
        ) {
//            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
            Text(text = "Post", modifier = Modifier.padding(start=80.dp,end=80.dp))
            when (val response = viewModel.uploadPost.value) {
                is Response.Loading -> {
                    CircularProgressIndicator()
                }
                is Response.Success -> {
                    if (response.data!!) {
                        LaunchedEffect(key1 = true) {
                            navController.navigate(ScreenRoutes.HomeScreen.route) {
                                popUpTo(ScreenRoutes.PostScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
                is Response.Error -> {
                    response.message?.let {
                        Toast(message = it)
                    }
                }
            }
        }
    }


}





