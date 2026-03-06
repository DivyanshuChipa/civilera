package com.example.civilera.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.civilera.data.Site
import com.example.civilera.ui.CivileraViewModel

@Composable
fun SiteListScreen(viewModel: CivileraViewModel, navController: NavController) {
    val sites by viewModel.allSites.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Sites", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(sites) { site ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        onClick = { navController.navigate("stock/${site.id}") }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = site.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = site.location, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddSiteDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, location ->
                viewModel.addSite(name, location)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddSiteDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Site") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Site Name") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = location, onValueChange = { location = it }, label = { Text("Location") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, location) }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
