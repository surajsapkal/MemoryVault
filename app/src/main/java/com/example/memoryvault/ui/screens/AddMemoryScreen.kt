package com.example.memoryvault.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScroll),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                            reminderTime = System.currentTimeMillis()
                        )
                    )
                }
            }
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}