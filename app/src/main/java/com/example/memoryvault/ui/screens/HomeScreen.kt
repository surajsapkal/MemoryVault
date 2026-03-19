package com.example.memoryvault.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memoryvault.domain.model.Memory
import com.example.memoryvault.ui.viewmodels.MemoryViewModel
import com.example.memoryvault.utils.Utils

@Composable
fun HomeScreen(
    viewmodel: MemoryViewModel = hiltViewModel(),
    onAddMemoryNavigation:() -> Unit,
    onMemoryDetailNavigation:(Long) -> Unit,
){

    val list = viewmodel.memoriesState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddMemoryNavigation()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        },
        // You can specify the FAB position (End is default)
        // floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ){
            if (list.value.isEmpty()){
                // show no memories UI
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No Memories!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }else{
                // show list UI
                LazyColumn {
                    items(list.value,{it.id}){ currentMemory ->

                        MemoryCard(currentMemory, onMemoryDetailNavigation)

                    }
                }
            }
        }
    }
}

@Composable
fun MemoryCard(currentMemory: Memory, onMemoryDetailNavigation: (Long) -> Unit) {

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp)
            .background(color = Color.White)
            .clickable(onClick = { onMemoryDetailNavigation(currentMemory.id) })
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = "created at ${Utils.convertLongToDateString(currentMemory.timestamp,"MM/dd/yyyy")}",
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

                Text(
                    text = currentMemory.title,
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
                    text = "Category",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = currentMemory.category,
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
                    text = "Content",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = currentMemory.content,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
    }

}