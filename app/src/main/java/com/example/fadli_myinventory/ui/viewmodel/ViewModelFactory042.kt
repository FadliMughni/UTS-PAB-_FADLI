package com.example.fadli_myinventory.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fadli_myinventory.data.db.AppDatabase042
import com.example.fadli_myinventory.data.repository.InventoryRepository042

class ViewModelFactory042 private constructor(
    private val repository042: InventoryRepository042
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel042::class.java) -> {
                AuthViewModel042(repository042) as T
            }
            modelClass.isAssignableFrom(InventoryViewModel042::class.java) -> {
                InventoryViewModel042(repository042) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE_042: ViewModelFactory042? = null

        fun getInstance042(context042: Context): ViewModelFactory042 {
            return INSTANCE_042 ?: synchronized(this) {
                val db042 = AppDatabase042.getDatabase042(context042)
                val repo042 = InventoryRepository042(db042.userDao042(), db042.itemDao042())
                val factory042 = ViewModelFactory042(repo042)
                INSTANCE_042 = factory042
                factory042
            }
        }
    }
}
