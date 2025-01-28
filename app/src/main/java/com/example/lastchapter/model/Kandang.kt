package com.example.lastchapter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kandang(
    @SerialName("id_kandang")
    val idKandang: String,
    val kapasitas: String,
    val lokasi: String,
)
