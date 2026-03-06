package com.example.civilera.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.civilera.data.CivileraRepository
import com.example.civilera.data.Material
import com.example.civilera.data.Site
import com.example.civilera.data.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CivileraViewModel(private val repository: CivileraRepository) : ViewModel() {

    val allSites: Flow<List<Site>> = repository.allSites
    val allMaterials: Flow<List<Material>> = repository.allMaterials
    val allTransactions: Flow<List<Transaction>> = repository.allTransactions

    fun addSite(name: String, location: String) {
        viewModelScope.launch {
            repository.insertSite(Site(name = name, location = location))
        }
    }

    fun addMaterial(name: String, unit: String) {
        viewModelScope.launch {
            repository.insertMaterial(Material(name = name, unit = unit))
        }
    }

    fun recordTransaction(materialId: Int, fromSiteId: Int?, toSiteId: Int, quantity: Double) {
        viewModelScope.launch {
            repository.insertTransaction(
                Transaction(
                    materialId = materialId,
                    fromSiteId = fromSiteId,
                    toSiteId = toSiteId,
                    quantity = quantity
                )
            )
        }
    }

    fun getStockForSite(siteId: Int): Flow<Map<Int, Double>> {
        return repository.allTransactions.map { transactions ->
            val stockMap = mutableMapOf<Int, Double>()
            transactions.forEach { trans ->
                if (trans.toSiteId == siteId) {
                    stockMap[trans.materialId] = (stockMap[trans.materialId] ?: 0.0) + trans.quantity
                }
                if (trans.fromSiteId == siteId) {
                    stockMap[trans.materialId] = (stockMap[trans.materialId] ?: 0.0) - trans.quantity
                }
            }
            stockMap
        }
    }
}

class CivileraViewModelFactory(private val repository: CivileraRepository) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CivileraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CivileraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
