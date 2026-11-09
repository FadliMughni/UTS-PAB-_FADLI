package com.example.fadli_myinventory.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_042",
    indices = [Index(value = ["username_042"], unique = true)]
)
data class User042(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_042")
    val id042: Int = 0,

    @ColumnInfo(name = "username_042")
    val username042: String,

    @ColumnInfo(name = "password_042")
    val password042: String
)
