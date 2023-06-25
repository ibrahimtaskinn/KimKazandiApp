package com.example.kimkazandiapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.data.entity.DetailData

@Database(entities = [Data::class, DetailData::class], version = 3, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun databaseDao(): DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}