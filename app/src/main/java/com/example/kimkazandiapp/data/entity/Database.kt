package com.example.kimkazandiapp.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val detail: String?,
    val basTarih: String?,
    val sonTarih: String?,
    val cekTarih: String?,
    val ilnTarih: String?,
    val minharcama: String?,
    val hediyeDeger: String?,
    val hediyeSayi: String?
)

@Entity
data class Data(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val detailUrl: String,
    val imgUrl: String,
    val title: String,
    val time: String,
    val hediye: String,
    val kosul: String,
    val tur: String,
    @Embedded(prefix = "detail_")
    val detailData: DetailData,
    var isFollowing: Boolean = false,
    var timestamp: Long = System.currentTimeMillis(),
)
