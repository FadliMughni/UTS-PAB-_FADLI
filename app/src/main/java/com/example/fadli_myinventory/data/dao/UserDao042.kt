package com.example.fadli_myinventory.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fadli_myinventory.data.entity.User042

@Dao
interface UserDao042 {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser042(user042: User042): Long

    @Query("SELECT * FROM users_042 WHERE username_042 = :username042 LIMIT 1")
    suspend fun getUserByUsername042(username042: String): User042?

    @Query("SELECT * FROM users_042 WHERE id_042 = :userId042 LIMIT 1")
    fun getUserById042(userId042: Int): LiveData<User042?>
}
