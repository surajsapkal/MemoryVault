package com.example.memoryvault.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.memoryvault.Greeting
import com.example.memoryvault.ui.ui.theme.MemoryVaultTheme
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ContentResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryVaultTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    // Read incoming parameter
                    val incomingParam = intent.getStringExtra("param_key")

                    Log.d("TAG","Intent Param: $incomingParam")
                    PreviewUI(
                        context,
                        modifier = Modifier.padding(innerPadding),
                        incomingParam!!,
                        setResult = { result, resultCode ->
                            val resultIntent = Intent().apply {
                                putExtra("return_key", result)
                            }
                            setResult(resultCode!!, resultIntent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewUI(context: Context,modifier: Modifier = Modifier, intentParam: String, setResult: (String?, Int?) -> Unit) {
    Column(
        modifier
            .fillMaxHeight(1f)
            .padding(20.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Tells the box to take up all space down to the button
            contentAlignment = Alignment.Center
        ) {

            val uri = remember(intentParam) {
                intentParam.toUri()
            }

            if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val type  = context.contentResolver.getType(uri)
                Log.d("TAG","Type: $type")

                when {
                    type != null && type.startsWith("image/") -> {
                        // show image preview
                        // Display it using Coil's AsyncImage
                        AsyncImage(
                            model = uri,
                            contentDescription = "Loaded image from Uri",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    type != null && type.startsWith("video/") -> {
                        // show exoplayer for video
                        // Declaring ExoPlayer
                        val mExoPlayer = remember(context) {
                            ExoPlayer.Builder(context).build().apply {
                                val dataSourceFactory = DefaultDataSourceFactory(
                                    context,
                                    Util.getUserAgent(context, context.packageName)
                                )
                                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                                    .createMediaSource(uri)
                                prepare(source)
                            }
                        }

                        // Implementing ExoPlayer
                        AndroidView(factory = { context ->
                            PlayerView(context).apply {
                                player = mExoPlayer
                            }
                        })
                    }
                    type != null && type.startsWith("audio/") -> {
                        // show audio player
                    }
                    type == "application/pdf" -> "PDF Document"
                    uri.scheme == "content" -> "Content Provider Data"
                    else -> "Unknown Type"
                }
            }
        }

        Button(
            onClick = {
                setResult("Success", Activity.RESULT_OK)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Optional padding around the button
        ) {
            Text("OK")
        }
    }
}