package com.example.fadli_myinventory.data.repository

import androidx.lifecycle.LiveData
import com.example.fadli_myinventory.data.dao.ItemDao042
import com.example.fadli_myinventory.data.dao.UserDao042
import com.example.fadli_myinventory.data.entity.Item042
import com.example.fadli_myinventory.data.entity.User042

class InventoryRepository042(
    private val userDao042: UserDao042,
    private val itemDao042: ItemDao042
) {
    suspend fun registerUser042(user042: User042): Long {
        return userDao042.registerUser042(user042)
    }

    suspend fun getUserByUsername042(username042: String): User042? {
        return userDao042.getUserByUsername042(username042)
    }

    fun getUserById042(userId042: Int): LiveData<User042?> {
        return userDao042.getUserById042(userId042)
    }

    suspend fun insertItem042(item042: Item042): Long {
        return itemDao042.insertItem042(item042)
    }

    suspend fun updateItem042(item042: Item042): Int {
        return itemDao042.updateItem042(item042)
    }

    suspend fun deleteItem042(item042: Item042): Int {
        return itemDao042.deleteItem042(item042)
    }

    fun getItemsByUserId042(userId042: Int): LiveData<List<Item042>> {
        return itemDao042.getItemsByUserId042(userId042)
    }

    fun searchItemsByUserId042(userId042: Int, query042: String): LiveData<List<Item042>> {
        return itemDao042.searchItemsByUserId042(userId042, query042)
    }

    suspend fun getItemById042(itemId042: Int): Item042? {
        return itemDao042.getItemById042(itemId042)
    }

    fun getTotalTypesByUserId042(userId042: Int): LiveData<Int> {
        return itemDao042.getTotalTypesByUserId042(userId042)
    }

    fun getTotalStockByUserId042(userId042: Int): LiveData<Int?> {
        return itemDao042.getTotalStockByUserId042(userId042)
    }

    fun getTotalValueByUserId042(userId042: Int): LiveData<Double?> {
        return itemDao042.getTotalValueByUserId042(userId042)
    }
}
