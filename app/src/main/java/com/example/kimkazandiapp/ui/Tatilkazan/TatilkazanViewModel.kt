package com.example.kimkazandiapp.ui.Tatilkazan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.data.repository.DatabaseDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TatilkazanViewModel @Inject constructor(
    private val databaseDaoRepository: DatabaseDaoRepository
) : ViewModel() {

    fun getFilteredData(tur: String): LiveData<List<Data>> {
        return databaseDaoRepository.getFilteredData(tur)
    }
}