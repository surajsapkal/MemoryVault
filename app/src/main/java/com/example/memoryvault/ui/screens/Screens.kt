package com.example.memoryvault.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Screens {

    @Composable
    fun HomeScreen(
        onMemoryNavigation:() -> Unit,
        onSearchNavigation:() -> Unit,
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    onMemoryNavigation()            }
            ) {
                Text(text = "Add Memory")
            }

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    onSearchNavigation()
                }
            ) {
                Text(text = "Search")
            }
        }
    }

    @Composable
    fun AddMemoryScreen(){
        Text(text = "Welcome to Add Memory", color = Color.White)
    }

    @Composable
    fun MemoryDetailScreen(){}

    @Composable
    fun SearchScreen(){
        Text(text = "Welcome to Search", color = Color.Blue)
    }

}