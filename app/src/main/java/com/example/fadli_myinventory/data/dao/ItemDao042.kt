package com.example.fadli_myinventory.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fadli_myinventory.data.entity.Item042

@Dao
interface ItemDao042 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem042(item042: Item042): Long

    @Update
    suspend fun updateItem042(item042: Item042): Int

    @Delete
    suspend fun deleteItem042(item042: Item042): Int

    @Query("SELECT * FROM items_042 WHERE user_id_042 = :userId042 ORDER BY id_042 DESC")
    fun getItemsByUserId042(userId042: Int): LiveData<List<Item042>>

    @Query("SELECT * FROM items_042 WHERE user_id_042 = :userId042 AND nama_042 LIKE '%' || :query042 || '%' ORDER BY id_042 DESC")
    fun searchItemsByUserId042(userId042: Int, query042: String): LiveData<List<Item042>>

    @Query("SELECT * FROM items_042 WHERE id_042 = :itemId042 LIMIT 1")
    suspend fun getItemById042(itemId042: Int): Item042?

    @Query("SELECT COUNT(*) FROM items_042 WHERE user_id_042 = :userId042")
    fun getTotalTypesByUserId042(userId042: Int): LiveData<Int>

    @Query("SELECT SUM(jumlah_042) FROM items_042 WHERE user_id_042 = :userId042")
    fun getTotalStockByUserId042(userId042: Int): LiveData<Int?>

    @Query("SELECT SUM(jumlah_042 * harga_042) FROM items_042 WHERE user_id_042 = :userId042")
    fun getTotalValueByUserId042(userId042: Int): LiveData<Double?>
}
