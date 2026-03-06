package com.example.civilera.data

import kotlinx.coroutines.flow.Flow

class CivileraRepository(
    private val siteDao: SiteDao,
    private val materialDao: MaterialDao,
    private val transactionDao: TransactionDao
) {
    val allSites: Flow<List<Site>> = siteDao.getAllSites()
    val allMaterials: Flow<List<Material>> = materialDao.getAllMaterials()
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insertSite(site: Site) = siteDao.insertSite(site)
    suspend fun insertMaterial(material: Material) = materialDao.insertMaterial(material)
    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insertTransaction(transaction)

    fun getTransactionsForSite(siteId: Int): Flow<List<Transaction>> = transactionDao.getTransactionsForSite(siteId)
}
