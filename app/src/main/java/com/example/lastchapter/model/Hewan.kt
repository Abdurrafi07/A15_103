package com.example.lastchapter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hewan(
    @SerialName("id_hewan")
    val idHewan: String,
    @SerialName("nama_hewan")
    val namaHewan: String,
    @SerialName("tipe_pakan")
    val tipePakan: String,
    val populasi: String,
    @SerialName("zona_wilayah")
    val zonaWilayah: String
)
