package com.example.memoryvault.ui.screens

import android.content.Context
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.android.volley.toolbox.ImageRequest
import com.example.memoryvault.R
import com.example.memoryvault.domain.model.Memory

@Composable
fun AddMemoryScreen(
    context: Context,
    onSubmitClick:(Memory) -> Unit,
){

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val rememberScroll = rememberScrollState()
    var isPickImage by remember { mutableStateOf(true) }

    var mUri: String? by remember { mutableStateOf("") }

    val singlePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            /* Handle the returned Uri */
            // store uri in room
            isPickImage = false
            mUri = uri?.toString()
        }
    )


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

        if (isPickImage){
            // show pick image UI
            OutlinedButton(
                modifier = Modifier.width(100.dp),
                onClick = {
                    // image capture/pick from gallery
                    //isPickImage = false
                    singlePhotoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text("Pick Image")
            }
        }else{
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
                        painter = rememberAsyncImagePainter(mUri),
                        contentDescription = "Preview Image", // Required for screen readers
                        modifier = Modifier.size(40.dp) // Set size, padding, etc.
                    )
                    // Image Text
                    Text(
                        text = "Image one line text here...",
                        fontSize = 16.sp,
                    )

                    // Cancel Image
                    Image(
                        painter = painterResource(id = R.drawable.cancel_img),
                        contentDescription = "Cancel Image", // Required for screen readers
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                isPickImage = true
                                mUri = ""
                            }
                    )
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
                    /*onSubmitClick(
                        Memory(
                            id = 0,
                            title = title,
                            content = content,
                            category = category,
                            timestamp = System.currentTimeMillis(),
                            reminderTime = System.currentTimeMillis()
                        )
                    )*/
                }
            }
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}