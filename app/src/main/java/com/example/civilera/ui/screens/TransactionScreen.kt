package com.example.civilera.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.civilera.data.Material
import com.example.civilera.data.Site
import com.example.civilera.ui.CivileraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(viewModel: CivileraViewModel) {
    val sites by viewModel.allSites.collectAsState(initial = emptyList())
    val materials by viewModel.allMaterials.collectAsState(initial = emptyList())

    var selectedMaterial by remember { mutableStateOf<Material?>(null) }
    var fromSite by remember { mutableStateOf<Site?>(null) } // null means initial stock
    var toSite by remember { mutableStateOf<Site?>(null) }
    var quantity by remember { mutableStateOf("") }

    var materialExpanded by remember { mutableStateOf(false) }
    var fromSiteExpanded by remember { mutableStateOf(false) }
    var toSiteExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Record Transaction", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Material Selection
        ExposedDropdownMenuBox(
            expanded = materialExpanded,
            onExpandedChange = { materialExpanded = !materialExpanded }
        ) {
            TextField(
                value = selectedMaterial?.name ?: "Select Material",
                onValueChange = {},
                readOnly = true,
                label = { Text("Material") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = materialExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = materialExpanded,
                onDismissRequest = { materialExpanded = false }
            ) {
                materials.forEach { material ->
                    DropdownMenuItem(
                        text = { Text(material.name) },
                        onClick = {
                            selectedMaterial = material
                            materialExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // From Site Selection
        ExposedDropdownMenuBox(
            expanded = fromSiteExpanded,
            onExpandedChange = { fromSiteExpanded = !fromSiteExpanded }
        ) {
            TextField(
                value = fromSite?.name ?: "Initial Entry (Purchase)",
                onValueChange = {},
                readOnly = true,
                label = { Text("From Site") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromSiteExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = fromSiteExpanded,
                onDismissRequest = { fromSiteExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Initial Entry (Purchase)") },
                    onClick = {
                        fromSite = null
                        fromSiteExpanded = false
                    }
                )
                sites.forEach { site ->
                    DropdownMenuItem(
                        text = { Text(site.name) },
                        onClick = {
                            fromSite = site
                            fromSiteExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // To Site Selection
        ExposedDropdownMenuBox(
            expanded = toSiteExpanded,
            onExpandedChange = { toSiteExpanded = !toSiteExpanded }
        ) {
            TextField(
                value = toSite?.name ?: "Select To Site",
                onValueChange = {},
                readOnly = true,
                label = { Text("To Site") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toSiteExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = toSiteExpanded,
                onDismissRequest = { toSiteExpanded = false }
            ) {
                sites.forEach { site ->
                    DropdownMenuItem(
                        text = { Text(site.name) },
                        onClick = {
                            toSite = site
                            toSiteExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val qty = quantity.toDoubleOrNull()
                if (selectedMaterial != null && toSite != null && qty != null) {
                    viewModel.recordTransaction(
                        materialId = selectedMaterial!!.id,
                        fromSiteId = fromSite?.id,
                        toSiteId = toSite!!.id,
                        quantity = qty
                    )
                    // Clear fields or show success
                    quantity = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Record Transaction")
        }
    }
}
