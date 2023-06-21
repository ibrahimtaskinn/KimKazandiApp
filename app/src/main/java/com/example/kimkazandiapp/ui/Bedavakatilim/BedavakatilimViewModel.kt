package com.example.kimkazandiapp.ui.Bedavakatilim


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.data.repository.DatabaseDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BedavakatilimViewModel @Inject constructor(
    private val databaseDaoRepository: DatabaseDaoRepository
) : ViewModel() {

    fun getFilteredData(tur: String): LiveData<List<Data>> {
        return databaseDaoRepository.getFilteredData(tur)
    }
}