package com.example.lastchapter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Monitoring(
    @SerialName("id_monitoring")
    val idMonitoring: String,
    @SerialName("tanggal_monitoring")
    val tanggalMonitoring: String,
    @SerialName("hewan_sakit")
    val hewanSakit: String,
    @SerialName("hewan_sehat")
    val hewanSehat: String,
    val status: String
)
