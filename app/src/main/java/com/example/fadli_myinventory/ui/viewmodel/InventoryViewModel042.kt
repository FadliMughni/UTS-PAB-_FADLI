package com.example.fadli_myinventory.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fadli_myinventory.data.entity.Item042
import com.example.fadli_myinventory.data.repository.InventoryRepository042
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InventoryViewModel042(private val repository042: InventoryRepository042) : ViewModel() {

    private val _searchQuery042 = MutableLiveData<String>("")
    private val _currentUserId042 = MutableLiveData<Int>()

    fun setUserId042(userId042: Int) {
        if (_currentUserId042.value != userId042) {
            _currentUserId042.value = userId042
        }
    }

    fun setSearchQuery042(query042: String) {
        _searchQuery042.value = query042
    }

    fun getItems042(userId042: Int, query042: String): LiveData<List<Item042>> {
        return if (query042.isBlank()) {
            repository042.getItemsByUserId042(userId042)
        } else {
            repository042.searchItemsByUserId042(userId042, query042)
        }
    }

    fun getTotalTypes042(userId042: Int): LiveData<Int> {
        return repository042.getTotalTypesByUserId042(userId042)
    }

    fun getTotalStock042(userId042: Int): LiveData<Int?> {
        return repository042.getTotalStockByUserId042(userId042)
    }

    fun getTotalValue042(userId042: Int): LiveData<Double?> {
        return repository042.getTotalValueByUserId042(userId042)
    }

    fun addItem042(
        userId042: Int,
        nama042: String,
        jumlahStr042: String,
        hargaStr042: String,
        onResult042: (Boolean, String) -> Unit
    ) {
        val trimmedNama042 = nama042.trim()
        if (trimmedNama042.isEmpty()) {
            onResult042(false, "Nama barang tidak boleh kosong")
            return
        }

        val jumlah042 = jumlahStr042.toIntOrNull()
        if (jumlah042 == null || jumlah042 < 0) {
            onResult042(false, "Jumlah barang harus angka dan tidak boleh negatif")
            return
        }

        val harga042 = hargaStr042.toDoubleOrNull()
        if (harga042 == null || harga042 < 0) {
            onResult042(false, "Harga barang harus angka dan tidak boleh negatif")
            return
        }

        val newItem042 = Item042(
            userId042 = userId042,
            nama042 = trimmedNama042,
            jumlah042 = jumlah042,
            harga042 = harga042
        )

        viewModelScope.launch(Dispatchers.IO) {
            val resultId042 = repository042.insertItem042(newItem042)
            withContext(Dispatchers.Main) {
                if (resultId042 > 0) {
                    onResult042(true, "Barang berhasil ditambahkan")
                } else {
                    onResult042(false, "Gagal menambahkan barang")
                }
            }
        }
    }

    fun updateItem042(
        itemId042: Int,
        userId042: Int,
        nama042: String,
        jumlahStr042: String,
        hargaStr042: String,
        onResult042: (Boolean, String) -> Unit
    ) {
        val trimmedNama042 = nama042.trim()
        if (trimmedNama042.isEmpty()) {
            onResult042(false, "Nama barang tidak boleh kosong")
            return
        }

        val jumlah042 = jumlahStr042.toIntOrNull()
        if (jumlah042 == null || jumlah042 < 0) {
            onResult042(false, "Jumlah barang harus angka dan tidak boleh negatif")
            return
        }

        val harga042 = hargaStr042.toDoubleOrNull()
        if (harga042 == null || harga042 < 0) {
            onResult042(false, "Harga barang harus angka dan tidak boleh negatif")
            return
        }

        val updatedItem042 = Item042(
            id042 = itemId042,
            userId042 = userId042,
            nama042 = trimmedNama042,
            jumlah042 = jumlah042,
            harga042 = harga042
        )

        viewModelScope.launch(Dispatchers.IO) {
            val rows042 = repository042.updateItem042(updatedItem042)
            withContext(Dispatchers.Main) {
                if (rows042 > 0) {
                    onResult042(true, "Barang berhasil diperbarui")
                } else {
                    onResult042(false, "Gagal memperbarui barang")
                }
            }
        }
    }

    fun deleteItem042(item042: Item042, onResult042: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val rows042 = repository042.deleteItem042(item042)
            withContext(Dispatchers.Main) {
                if (rows042 > 0) {
                    onResult042(true, "Barang berhasil dihapus")
                } else {
                    onResult042(false, "Gagal menghapus barang")
                }
            }
        }
    }

    fun getItemById042(itemId042: Int, onResult042: (Item042?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val item042 = repository042.getItemById042(itemId042)
            withContext(Dispatchers.Main) {
                onResult042(item042)
            }
        }
    }
}
