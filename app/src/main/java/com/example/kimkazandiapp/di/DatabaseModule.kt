package com.example.kimkazandiapp.di

import android.app.Application
import com.example.kimkazandiapp.room.DatabaseDao
import com.example.kimkazandiapp.room.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabaseDao(database: MyDatabase): DatabaseDao {
        return database.databaseDao()
    }

    @Provides
    fun provideMyDatabase(application: Application): MyDatabase {
        return MyDatabase.getDatabase(application)
    }
}