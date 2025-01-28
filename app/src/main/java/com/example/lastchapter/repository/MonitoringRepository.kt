package com.example.lastchapter.repository

import com.example.lastchapter.model.Monitoring
import com.example.lastchapter.service.MonitoringService
import java.io.IOException

interface MonitoringRepository {
    suspend fun getMonitoring(): List<Monitoring>
    suspend fun insertMonitoring(monitoring: Monitoring)
    suspend fun updateMonitoring(idMonitoring: String, monitoring: Monitoring)
    suspend fun deleteMonitoring(idMonitoring: String)
    suspend fun getMonitoringById(idMonitoring: String): Monitoring
}

class NetworkMonitoringRepository(
    private val monitoringApiService: MonitoringService
) : MonitoringRepository {
    override suspend fun getMonitoring(): List<Monitoring> =
        monitoringApiService.getMonitoring()

    override suspend fun insertMonitoring(monitoring: Monitoring) {
        monitoringApiService.insertMonitoring(monitoring)
    }

    override suspend fun updateMonitoring(idMonitoring: String, monitoring: Monitoring) {
        monitoringApiService.updateMonitoring(idMonitoring, monitoring)
    }

    override suspend fun deleteMonitoring(idMonitoring: String) {
        try {
            val response = monitoringApiService.deleteMonitoring(idMonitoring)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Monitoring. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMonitoringById(idMonitoring: String): Monitoring {
        try {
            return monitoringApiService.getMonitoringById(idMonitoring)
        } catch (e: IOException) {
            throw IOException("Failed to fetch Monitoring with ID: $idMonitoring. Network error occurred.", e)
        }
    }

}