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
import androidx.compose.material3.TextField
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
    onGetOtp:(String) -> Unit,
    onVerifyOtp:(String) -> Unit,
){

    val mobileNUm = remember { mutableStateOf("") }
    val d1 = remember { mutableStateOf("") }
    val d2 = remember { mutableStateOf("") }
    val d3 = remember { mutableStateOf("") }
    val d4 = remember { mutableStateOf("") }
    val d5 = remember { mutableStateOf("") }
    val d6 = remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(10.dp),
        ){
            Text(
                text = "Enter Mobile Number",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = mobileNUm.value,
                onValueChange = { it ->
                    if (it.length <= 10) {
                        mobileNUm.value = it
                    }
                },
                label = { Text("Enter Here") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                    imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                ),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Button(
                    onClick = {
                        if (mobileNUm.value.isEmpty()){
                            Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show()
                        }else{
                            onGetOtp("+91${mobileNUm.value}")
                        }
                    },
                    modifier = Modifier.width(100.dp)
                ) {
                    Text("Get OTP")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Enter OTP",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // digit 1
                TextField(
                    modifier = Modifier.width(60.dp),
                    value = d1.value,
                    onValueChange = { it ->
                        if (it.length <= 1) {
                            d1.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                        imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                    ),
                    singleLine = true,
                )

                // digit 2
                TextField(
                    modifier = Modifier.width(60.dp),
                    value = d2.value,
                    onValueChange = { it ->
                        if (it.length <= 1) {
                            d2.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                        imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                    ),
                    singleLine = true,
                )

                // digit 3
                TextField(
                    modifier = Modifier.width(60.dp),
                    value = d3.value,
                    onValueChange = { it ->
                        if (it.length <= 1) {
                            d3.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                        imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                    ),
                    singleLine = true,
                )

                // digit 4
                TextField(
                    modifier = Modifier.width(60.dp),
                    value = d4.value,
                    onValueChange = { it ->
                        if (it.length <= 1) {
                            d4.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                        imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                    ),
                    singleLine = true,
                )

                // digit 5
                TextField(
                    modifier = Modifier.width(60.dp),
                    value = d5.value,
                    onValueChange = { it ->
                        if (it.length <= 1) {
                            d5.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                        imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                    ),
                    singleLine = true,
                )

                // digit 6
                TextField(
                    modifier = Modifier.width(60.dp),
                    value = d6.value,
                    onValueChange = { it ->
                        if (it.length <= 1) {
                            d6.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, // Opens an email-optimized keyboard
                        imeAction = ImeAction.Next // Shows a "Next" button instead of "Enter"
                    ),
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Button(
                    onClick = {
                        if (d1.value.isEmpty() || d2.value.isEmpty()
                            || d3.value.isEmpty() || d4.value.isEmpty()
                            || d5.value.isEmpty() || d6.value.isEmpty()
                            ){
                            Toast.makeText(context, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
                        }else{
                            val otp = d1.value+d2.value+d3.value+d4.value+d5.value+d6.value
                            onVerifyOtp(otp)
                        }
                    },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Verify OTP")
                }
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
