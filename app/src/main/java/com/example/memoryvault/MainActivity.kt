package com.example.memoryvault

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.WorkManager
import com.example.memoryvault.ui.screens.AddMemoryScreen
import com.example.memoryvault.ui.screens.HomeScreen
import com.example.memoryvault.ui.screens.LoginScreen
import com.example.memoryvault.ui.screens.MemoryDetailScreen
import com.example.memoryvault.ui.screens.SearchScreen
import com.example.memoryvault.ui.theme.MemoryVaultTheme
import com.example.memoryvault.ui.viewmodels.MemoryViewModel
import com.example.memoryvault.utils.Routes
import com.example.memoryvault.utils.WorkManagerHelper
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
fun AppNavigator() {
    val navController = rememberNavController()
    val viewModel: MemoryViewModel = hiltViewModel()
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val auth = remember { FirebaseAuth.getInstance() }

    // Track the currently logged in user
    var user by remember { mutableStateOf(auth.currentUser) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        if (user != null) {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (user != null){
            // already logged in
            Routes.Home.route
        }else{
            Routes.Login.route
        },
        builder = {
            composable(Routes.Login.route){
                LoginScreen(
                    context,
                    onGoogleSignIn = {

                        if (user != null){
                            // already logged in
                            // go to home screen
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                            }
                        }else{
                            // show login screen
                            scope.launch {
                                isLoading = true
                                errorMessage = null

                                try {
                                    val firebaseUser = signInWithGoogle(context)
                                    user = firebaseUser

                                    if (firebaseUser != null) {
                                        navController.navigate(Routes.Home.route) {
                                            popUpTo(Routes.Login.route) {
                                                inclusive = true
                                            }
                                        }
                                    }

                                } catch (e: NoCredentialException){
                                    launcher.launch(
                                        Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                                            putExtra(Settings.EXTRA_ACCOUNT_TYPES,arrayOf("com.google"))
                                        }
                                    )
                                } catch (e: Exception) {
                                    Log.e("GoogleSignIn", "Google Sign-In failed", e)
                                    errorMessage = e.localizedMessage ?: "Authentication Failed"
                                } finally {
                                    isLoading = false
                                }
                            }
                        }

                        Log.d("TAG","Sign In with Google")
                    }
                )
            }

            composable(Routes.Home.route){
                HomeScreen(
                    onAddMemoryNavigation = {
                        navController.navigate(Routes.AddMemory.route)
                    },
                    onMemoryDetailNavigation = { memoryId ->
                        navController.navigate(
                            Routes.MemoryDetails.createRoute(memoryId)
                        )
                    }
                )
            }

            composable(Routes.AddMemory.route){
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
                        type = NavType.StringType
                    }
                )
            ){
                val id = it.arguments?.getString("memoryId") ?: ""
                MemoryDetailScreen(
                    id,
                    onUpdate = {
                        WorkManagerHelper.scheduleSync(context)
                        navController.popBackStack()  //to remove from stack
                    },
                    onDelete = {
                        WorkManagerHelper.scheduleSync(context)
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

suspend fun signInWithGoogle(context: Context): com.google.firebase.auth.FirebaseUser? {
    val credentialManager = CredentialManager.create(context)

    // Retrieve your Web Client ID from the Google Cloud / Firebase console
    // Ensure you use the Client ID labeled as "Web application"

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false) // Set true to only show accounts already linked to your app
        .setServerClientId(getString(context,R.string.web_client_id))
        .setAutoSelectEnabled(true) // Automatically logs user in if only one account exists
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    // Show the bottom sheet to the user
    val result = credentialManager.getCredential(context = context, request = request)

    // Parse the token from the resulting credential
    val credential = result.credential
    if (credential.type == com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        val googleIdTokenCredential = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.data)
        val idToken = googleIdTokenCredential.idToken

        // Authenticate with Firebase using the ID token
        val authCredential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = FirebaseAuth.getInstance().signInWithCredential(authCredential).await()
        return authResult.user
    } else {
        throw Exception("Received an unexpected credential type.")
    }
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