package com.example.luckyfanapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.luckyfanapp.dao.UserDao
import com.example.luckyfanapp.model.User

@Database(entities = [User::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null

        fun getDb(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "UserDb"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
