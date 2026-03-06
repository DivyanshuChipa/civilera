package com.example.civilera.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.civilera.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CivileraApp(viewModel: CivileraViewModel) {
    val navController = rememberNavController()
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Civilera") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Sites") },
                    label = { Text("Sites") },
                    selected = false,
                    onClick = { navController.navigate("sites") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Materials") },
                    label = { Text("Materials") },
                    selected = false,
                    onClick = { navController.navigate("materials") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Send, contentDescription = "Transactions") },
                    label = { Text("Transactions") },
                    selected = false,
                    onClick = { navController.navigate("transactions") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("sites") { SiteListScreen(viewModel, navController) }
            composable("materials") { MaterialListScreen(viewModel) }
            composable("transactions") { TransactionScreen(viewModel) }
            composable("stock/{siteId}") { backStackEntry ->
                val siteId = backStackEntry.arguments?.getString("siteId")?.toIntOrNull() ?: 0
                StockViewScreen(siteId, viewModel)
            }
        }
    }
}
