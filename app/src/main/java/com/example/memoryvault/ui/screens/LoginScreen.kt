package com.example.memoryvault.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoryvault.ui.theme.MemoryVaultTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    context: Context,
    onGoogleSignIn: () -> Unit,
){

    Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

            Button(
                modifier = Modifier.width(200.dp),
                onClick = { onGoogleSignIn() }
            ) {
                Text("Google SignIn")
            }

        }
    }

}

/*@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MemoryVaultTheme {
        LoginScreen()
    }
}*/
