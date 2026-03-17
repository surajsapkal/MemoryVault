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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memoryvault.ui.screens.AddMemoryScreen
import com.example.memoryvault.ui.screens.HomeScreen
import com.example.memoryvault.ui.screens.MemoryDetailScreen
import com.example.memoryvault.ui.screens.SearchScreen
import com.example.memoryvault.ui.theme.MemoryVaultTheme
import com.example.memoryvault.ui.viewmodels.MemoryViewModel
import com.example.memoryvault.utils.Routes
import com.example.memoryvault.utils.Routes.ADD_MEMORY
import com.example.memoryvault.utils.Routes.HOME
import com.example.memoryvault.utils.Routes.MEMORY_DETAIL
import com.example.memoryvault.utils.Routes.SEARCH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    val viewModel: MemoryViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = HOME,
        builder = {
            composable(HOME){
                HomeScreen(
                    onMemoryNavigation = {
                        navController.navigate(ADD_MEMORY)
                    },
                    onSearchNavigation = {
                        navController.navigate(SEARCH)
                    }
                )
            }

            composable(ADD_MEMORY){


                AddMemoryScreen(LocalContext.current){memory ->
                    // on submit handle
                    viewModel.insertMemory(memory)
                    navController.popBackStack() //to remove from stack
                }
            }
            composable(MEMORY_DETAIL){
                MemoryDetailScreen()
            }
            composable(SEARCH){
                SearchScreen()
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