package com.example.fadli_myinventory.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "items_042",
    foreignKeys = [
        ForeignKey(
            entity = User042::class,
            parentColumns = ["id_042"],
            childColumns = ["user_id_042"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Item042(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_042")
    val id042: Int = 0,

    @ColumnInfo(name = "user_id_042", index = true)
    val userId042: Int,

    @ColumnInfo(name = "nama_042")
    val nama042: String,

    @ColumnInfo(name = "jumlah_042")
    val jumlah042: Int,

    @ColumnInfo(name = "harga_042")
    val harga042: Double
)
