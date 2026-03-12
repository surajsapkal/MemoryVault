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
import androidx.compose.ui.unit.dp

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