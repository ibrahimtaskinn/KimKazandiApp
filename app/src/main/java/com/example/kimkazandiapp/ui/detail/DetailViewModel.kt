package com.example.kimkazandiapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.data.repository.DatabaseDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val databaseDaoRepository: DatabaseDaoRepository
) : ViewModel() {

    private val _data = MutableLiveData<Data>()
    val data: LiveData<Data> = _data

    fun getData(id: Int) {
        viewModelScope.launch {
            val data = databaseDaoRepository.getData(id)
            _data.postValue(data)
        }
    }
}