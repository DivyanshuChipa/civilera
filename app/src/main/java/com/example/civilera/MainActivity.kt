package com.example.civilera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.civilera.data.CivileraDatabase
import com.example.civilera.data.CivileraRepository
import com.example.civilera.ui.CivileraApp
import com.example.civilera.ui.CivileraViewModel
import com.example.civilera.ui.CivileraViewModelFactory
import com.example.civilera.ui.theme.CivileraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = CivileraDatabase.getDatabase(this)
        val repository = CivileraRepository(
            database.siteDao(),
            database.materialDao(),
            database.transactionDao()
        )
        val viewModel: CivileraViewModel by viewModels {
            CivileraViewModelFactory(repository)
        }

        enableEdgeToEdge()
        setContent {
            CivileraTheme {
                CivileraApp(viewModel)
            }
        }
    }
}
