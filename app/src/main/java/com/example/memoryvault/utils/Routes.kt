package com.example.memoryvault.utils

sealed class Routes(val route: String){
    data object Home: Routes("home")
    data object AddMemory: Routes("add_memory")
    data object Search: Routes("search")
    data object MemoryDetails: Routes("memory_detail/{memoryId}"){
        fun createRoute(memoryId: Long) = "memory_detail/$memoryId"
    }
}