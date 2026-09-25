package com.example.memoryvault.ui.screens

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.videoFrameMillis
import com.android.volley.toolbox.ImageRequest
import com.example.memoryvault.R
import com.example.memoryvault.domain.model.Memory
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemoryScreen(
    context: Context,
    onSubmitClick:(Memory) -> Unit,
){

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val rememberScroll = rememberScrollState()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var isPickImage by remember { mutableStateOf(true) }
    var isPickVideo by remember { mutableStateOf(true) }
    val context = LocalContext.current

    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var showPickerSheet by remember { mutableStateOf(false) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }
    var pendingVideoUri by remember { mutableStateOf<Uri?>(null) }
    var imageBottomSheetText by remember { mutableStateOf(false) }
    var videoBottomSheetText by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedUri = uri
            isPickImage = false
            isPickVideo = false
        }
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showPickerSheet = false
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedUri = pendingCameraUri
            isPickImage = false
            isPickVideo = false
        } else {
            pendingCameraUri = null
        }
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showPickerSheet = false
            }
        }
    }

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success) {
            selectedUri = pendingVideoUri
            isPickImage = false
            isPickVideo = false
        } else {
            pendingVideoUri = null
        }
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showPickerSheet = false
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScroll),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Title",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            value = title,
            onValueChange = { newText ->
                title = newText
            },
            label = { Text("Enter Here") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Opens an email-optimized keyboard
                imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Category",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            value = category,
            onValueChange = { newText ->
                category = newText
            },
            label = { Text("Enter Here") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Opens an email-optimized keyboard
                imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Content",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            modifier = Modifier.height(80.dp),
            value = content,
            onValueChange = { newText ->
                content = newText
            },
            label = { Text("Enter Here") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Opens an email-optimized keyboard
                imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
            ),
        )

        Spacer(modifier = Modifier.height(14.dp))

        when{
            selectedUri == null -> {
                // outline buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // show pick image UI
                    OutlinedButton(
                        modifier = Modifier.width(180.dp),
                        onClick = {
                            // image capture/pick from gallery
                            //isPickImage = false
                            //singlePhotoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            showPickerSheet = true

                            imageBottomSheetText = true
                            videoBottomSheetText = false
                        }
                    ) {
                        Text("Pick Image")
                    }

                    // show pick video UI
                    OutlinedButton(
                        modifier = Modifier.width(180.dp),
                        onClick = {
                            // image capture/pick from gallery
                            //isPickImage = false
                            //singlePhotoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            showPickerSheet = true

                            imageBottomSheetText = false
                            videoBottomSheetText = true
                        }
                    ) {
                        Text("Pick Video")
                    }
                }
            }
            imageBottomSheetText -> {
                // image captured preview with cancel
                // show cancel image UI
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Preview Image
                        Image(
                            painter = rememberAsyncImagePainter(selectedUri),
                            contentDescription = "Preview Image", // Required for screen readers
                            modifier = Modifier.size(40.dp) // Set size, padding, etc.
                        )
                        // Image Text
                        Text(
                            text = getFileName(context,selectedUri!!) ?: "file_name",
                            fontSize = 16.sp,
                        )

                        // Cancel Image
                        Image(
                            painter = painterResource(id = R.drawable.cancel_img),
                            contentDescription = "cancelImage", // Required for screen readers
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    isPickImage = true
                                    isPickVideo = true
                                    selectedUri = null
                                }
                        )
                    }
                }
            }
            else -> {
                // video captured preview with cancel
                // show cancel video UI
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Preview Video
                        Image(
                            painter = rememberAsyncImagePainter(
                                coil.request.ImageRequest.Builder(context)
                                    .data(selectedUri)
                                    .decoderFactory(VideoFrameDecoder.Factory())
                                    .videoFrameMillis(0)
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "Preview Video", // Required for screen readers
                            modifier = Modifier.size(40.dp) // Set size, padding, etc.
                        )
                        // Image Text
                        Text(
                            text = getFileName(context,selectedUri!!) ?: "file_name",
                            fontSize = 16.sp,
                        )

                        // Cancel Video
                        Image(
                            painter = painterResource(id = R.drawable.cancel_img),
                            contentDescription = "cancelVideo", // Required for screen readers
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    isPickImage = true
                                    isPickVideo = true
                                    selectedUri = null
                                }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            modifier = Modifier.width(100.dp),
            onClick = {
                if (title.isEmpty() || category.isEmpty() || content.isEmpty()){
                    // show validation toast
                    Toast.makeText(context, "Please fill the fields!", Toast.LENGTH_SHORT).show()
                }else{
                    // submit / insert memory
                    onSubmitClick(
                        Memory(
                            id = 0,
                            title = title,
                            content = content,
                            category = category,
                            timestamp = System.currentTimeMillis(),
                            reminderTime = System.currentTimeMillis(),
                            isSynced = false,
                            type = if (selectedUri != null) "image" else "text",
                            imageUri = selectedUri?.toString()
                        )
                    )
                }
            }
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (showPickerSheet) {
            ModalBottomSheet(
                modifier = Modifier.padding(10.dp),
                onDismissRequest = {
                    showPickerSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    // Gallery Image/Video
                    Column(
                        modifier = Modifier.clickable {
                            /*scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showPickerSheet = false
                                }
                            }*/
                            if (imageBottomSheetText) galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            if (videoBottomSheetText) galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                        },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.gallery_icon),
                                contentDescription = "galleryIcon",
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Text
                        Text(
                            "Pick from Gallery",
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    // Capture Image/Record Video
                    Column(
                        modifier = Modifier.clickable {
                            /*scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showPickerSheet = false
                                }
                            }*/
                            if (imageBottomSheetText){
                                val uri = createImageUri(context)
                                pendingCameraUri = uri
                                cameraLauncher.launch(uri)
                            }

                            if (videoBottomSheetText){
                                val uri = createVideoUri(context)
                                pendingVideoUri = uri
                                videoLauncher.launch(uri)
                            }
                        },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.capture_icon),
                                contentDescription = "captureIcon",
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Text
                        Text(
                            text = if (imageBottomSheetText)"Capture Image" else "Record Video",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        /*if (showPickerSheet) {
            ModalBottomSheet(onDismissRequest = { showPickerSheet = false }) {
                ListItem(
                    headlineContent = { Text("Take photo") },
                    modifier = Modifier.clickable {
                        showPickerSheet = false
                        val uri = createImageUri(context) // function below
                        pendingCameraUri = uri
                        cameraLauncher.launch(uri)
                    }
                )
                ListItem(
                    headlineContent = { Text("Choose from gallery") },
                    modifier = Modifier.clickable {
                        showPickerSheet = false
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
        }*/

    }
}

fun createImageUri(context: Context): Uri {
    val imagesDir = File(context.cacheDir, "images").apply { mkdirs() }
    val imageFile = File(imagesDir, "IMG_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}

fun createVideoUri(context: Context): Uri {
    val videoDir = File(context.cacheDir, "videos").apply { mkdirs() }
    val videoFile = File(videoDir, "VID_${System.currentTimeMillis()}.mp4")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        videoFile
    )
}

fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                // Get the column index for the display name
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    result = cursor.getString(nameIndex)
                }
            }
        }
    }
    // Fallback for file:// URIs or if content query fails
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result
}