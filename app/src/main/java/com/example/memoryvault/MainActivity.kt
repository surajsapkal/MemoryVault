package com.example.memoryvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.WorkManager
import com.example.memoryvault.ui.screens.AddMemoryScreen
import com.example.memoryvault.ui.screens.HomeScreen
import com.example.memoryvault.ui.screens.MemoryDetailScreen
import com.example.memoryvault.ui.screens.SearchScreen
import com.example.memoryvault.ui.theme.MemoryVaultTheme
import com.example.memoryvault.ui.viewmodels.MemoryViewModel
import com.example.memoryvault.utils.Routes
import com.example.memoryvault.utils.WorkManagerHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryVaultTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigator(workManager)
                }
            }
        }
    }
}

@Composable
fun AppNavigator(workManager: WorkManager) {
    val navController = rememberNavController()
    val viewModel: MemoryViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        builder = {
            composable(Routes.Home.route){
                HomeScreen(
                    onAddMemoryNavigation = {
                        navController.navigate(Routes.AddMemory.route)
                    },
                    onMemoryDetailNavigation = { memoryId ->
                        navController.navigate(Routes.MemoryDetails.createRoute(memoryId))
                    }
                )
            }

            composable(Routes.AddMemory.route){

                val context = LocalContext.current
                AddMemoryScreen(LocalContext.current){memory ->
                    // on submit handle
                    viewModel.insertMemory(memory)
                    WorkManagerHelper.scheduleSync(context)
                    navController.popBackStack() //to remove from stack
                }
            }
            composable(
                route = Routes.MemoryDetails.route,
                arguments = listOf(
                    navArgument("memoryId"){
                        type = NavType.LongType
                    }
                )
            ){
                val id = it.arguments?.getLong("memoryId") ?: 0L
                MemoryDetailScreen(
                    id,
                    onUpdate = {
                        navController.popBackStack()  //to remove from stack
                    },
                    onDelete = {
                        navController.popBackStack()  //to remove from stack
                    }
                )
            }
            composable(Routes.Search.route){
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