package com.example.kimkazandiapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kimkazandiapp.data.entity.Data

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: Data)

    @Query("SELECT * FROM Data")
    suspend fun getAllData(): List<Data>

    @Query("SELECT * FROM Data WHERE tur = :tur")
    suspend fun getFilteredData(tur: String): List<Data>

    @Query("SELECT * FROM Data WHERE id = :id")
    suspend fun getData(id: Int): Data

    @Query("SELECT * FROM Data WHERE detailUrl = :detailUrl AND tur = :tur")
    suspend fun getDataByUrlAndTur(detailUrl: String, tur: String): Data?

    @Update
    suspend fun updateData(data: Data)

    @Query("DELETE FROM Data")
    suspend fun deleteAllData()
}