package com.example.lastchapter.repository

import com.example.lastchapter.service.HewanService
import com.example.lastchapter.service.KandangService
import com.example.lastchapter.service.MonitoringService
import com.example.lastchapter.service.PetugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val petugasRepository: PetugasRepository
    val hewanRepository: HewanRepository
    val kandangRepository: KandangRepository
    val monitoringRepository: MonitoringRepository
}

class SatoContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2/sato2/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val petugasService: PetugasService by lazy { retrofit.create(PetugasService::class.java) }
    override val petugasRepository: PetugasRepository by lazy { NetworkPetugasRepository(petugasService) }

    private val hewanService: HewanService by lazy { retrofit.create(HewanService::class.java) }
    override val hewanRepository: HewanRepository by lazy { NetworkHewanRepository(hewanService) }

    private val kandangService: KandangService by lazy { retrofit.create(KandangService::class.java) }
    override val kandangRepository: KandangRepository by lazy { NetworkKandangRepository(kandangService) }

    private val monitoringService: MonitoringService by lazy { retrofit.create(MonitoringService::class.java) }
    override val monitoringRepository: MonitoringRepository by lazy { NetworkMonitoringRepository(monitoringService) }
}
