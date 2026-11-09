package com.example.fadli_myinventory.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fadli_myinventory.data.dao.ItemDao042
import com.example.fadli_myinventory.data.dao.UserDao042
import com.example.fadli_myinventory.data.entity.Item042
import com.example.fadli_myinventory.data.entity.User042

@Database(
    entities = [User042::class, Item042::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase042 : RoomDatabase() {

    abstract fun userDao042(): UserDao042
    abstract fun itemDao042(): ItemDao042

    companion object {
        @Volatile
        private var INSTANCE_042: AppDatabase042? = null

        fun getDatabase042(context042: Context): AppDatabase042 {
            return INSTANCE_042 ?: synchronized(this) {
                val instance042 = Room.databaseBuilder(
                    context042.applicationContext,
                    AppDatabase042::class.java,
                    "inventory_database_042"
                ).build()
                INSTANCE_042 = instance042
                instance042
            }
        }
    }
}
