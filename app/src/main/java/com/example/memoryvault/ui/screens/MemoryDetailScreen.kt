package com.example.memoryvault.ui.screens

import android.R.attr.text
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.ui.viewmodels.MemoryViewModel
import com.example.memoryvault.utils.Utils
import com.example.memoryvault.utils.Utils.showToast

@Composable
fun MemoryDetailScreen(
    memoryId: Long,
    onUpdate:() -> Unit,
    onDelete:() -> Unit,
    viewmodel: MemoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(memoryId) {
        viewmodel.getMemoryById(memoryId)
    }

    val currentMemory = viewmodel.selectedMemory.collectAsState().value
    val uiEvent = viewmodel.uiEvent.collectAsState().value

    LaunchedEffect(uiEvent) {
        uiEvent?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewmodel.clearEvent()
        }
    }

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(currentMemory) {
        currentMemory?.let {
            title = it.title
            category = it.category
            content = it.content
        }
    }

    if (currentMemory == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading...")
        }
        return
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(color = Color.White),
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = "created at ${Utils.convertLongToDateString(currentMemory?.timestamp ?: 0L,"MM/dd/yyyy")}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = "Title",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { newText -> title = newText },
                    label = { Text("Enter Here") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = "Category",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { newText -> category = newText },
                    label = { Text("Enter Here") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = "Content",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { newText -> content = newText },
                    label = { Text("Enter Here") }
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    modifier = Modifier.background(
                        color = Color.Green,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    onClick = {
                        viewmodel.updateMemory(
                            currentMemory.copy(
                                title = title,
                                category = category,
                                content = content,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        onUpdate()
                    }) {
                    Text(
                        text = "Update",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    modifier = Modifier.background(
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    onClick = {
                        viewmodel.deleteMemory(
                            memory = currentMemory!!
                        )
                        onDelete()
                    }) {
                    Text(
                        text = "Delete",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

        }
    }

}