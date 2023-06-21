package com.example.kimkazandiapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.room.DatabaseDao
import javax.inject.Inject

class DatabaseDaoRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    suspend fun insertData(data: Data) {
        val existingData = databaseDao.getDataByUrlAndTur(data.detailUrl, data.tur)
        if (existingData == null) {
            databaseDao.insertData(data)
        }
    }

    fun getAllData(): LiveData<List<Data>> = liveData {
        emit(databaseDao.getAllData())
    }

    fun getFilteredData(tur: String): LiveData<List<Data>> = liveData {
        emit(databaseDao.getFilteredData(tur))
    }

    suspend fun getData(id: Int): Data {
        return databaseDao.getData(id)
    }

    suspend fun deleteAllData() {
        databaseDao.deleteAllData()
    }
}