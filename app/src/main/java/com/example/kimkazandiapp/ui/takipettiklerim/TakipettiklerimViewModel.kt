package com.example.kimkazandiapp.ui.takipettiklerim

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.data.repository.DatabaseDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TakipettiklerimViewModel @Inject constructor(
    private val databaseDaoRepository: DatabaseDaoRepository
) : ViewModel() {

    val followedData: LiveData<List<Data>> = databaseDaoRepository.getFollowingData()
}