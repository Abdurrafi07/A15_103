package com.example.lastchapter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Petugas(
    @SerialName("id_petugas")
    val idPetugas: String,
    @SerialName("nama_petugas")
    val namaPetugas: String,
    val jabatan: String
)
