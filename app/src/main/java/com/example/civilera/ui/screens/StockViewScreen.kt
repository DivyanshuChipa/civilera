package com.example.civilera.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.civilera.ui.CivileraViewModel

@Composable
fun StockViewScreen(siteId: Int, viewModel: CivileraViewModel) {
    val stock by viewModel.getStockForSite(siteId).collectAsState(initial = emptyMap())
    val materials by viewModel.allMaterials.collectAsState(initial = emptyList())
    val sites by viewModel.allSites.collectAsState(initial = emptyList())
    
    val siteName = sites.find { it.id == siteId }?.name ?: "Site $siteId"

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Stock at $siteName", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        if (stock.isEmpty()) {
            Text("No stock available at this site.")
        } else {
            LazyColumn {
                items(stock.toList()) { (materialId, quantity) ->
                    val material = materials.find { it.id == materialId }
                    if (material != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = material.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = "$quantity ${material.unit}", style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
