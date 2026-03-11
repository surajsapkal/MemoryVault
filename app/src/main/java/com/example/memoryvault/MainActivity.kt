package com.example.memoryvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memoryvault.ui.screens.Screens
import com.example.memoryvault.ui.theme.MemoryVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryVaultTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home",
        builder = {
            composable("home"){
                Screens().HomeScreen(
                    onMemoryNavigation = {
                        navController.navigate("add_memory")
                    },
                    onSearchNavigation = {
                        navController.navigate("search")
                    }
                )
            }

            composable("add_memory"){
                Screens().AddMemoryScreen()
            }
            composable("memory_details"){
                Screens().MemoryDetailScreen()
            }
            composable("search"){
                Screens().SearchScreen()
            }
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemoryVaultTheme {
        Greeting("Android")
    }
}