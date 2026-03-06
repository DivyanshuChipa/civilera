package com.example.civilera.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.civilera.data.Material
import com.example.civilera.ui.CivileraViewModel

@Composable
fun MaterialListScreen(viewModel: CivileraViewModel) {
    val materials by viewModel.allMaterials.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Materials", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(materials) { material ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = material.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Unit: ${material.unit}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddMaterialDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, unit ->
                viewModel.addMaterial(name, unit)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddMaterialDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Material") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Material Name") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = unit, onValueChange = { unit = it }, label = { Text("Unit (e.g., kg, bags)") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, unit) }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
