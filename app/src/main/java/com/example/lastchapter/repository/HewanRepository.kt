package com.example.lastchapter.repository

import com.example.lastchapter.model.Hewan
import com.example.lastchapter.service.HewanService
import java.io.IOException

interface HewanRepository {
    suspend fun getHewan(): List<Hewan>
    suspend fun insertHewan(hewan: Hewan)
    suspend fun updateHewan(idHewan: String, hewan: Hewan)
    suspend fun deleteHewan(idHewan: String)
    suspend fun getHewanById(idHewan: String): Hewan
}

class NetworkHewanRepository(
    private val hewanApiService: HewanService
) : HewanRepository {
    override suspend fun getHewan(): List<Hewan> =
        hewanApiService.getHewan()


    override suspend fun insertHewan(hewan: Hewan) {
        hewanApiService.insertHewan(hewan)
    }

    override suspend fun updateHewan(idHewan: String, hewan: Hewan) {
        hewanApiService.updateHewan(idHewan, hewan)
    }

    override suspend fun deleteHewan(idHewan: String) {
        try {
            val response = hewanApiService.deleteHewan(idHewan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Hewan. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getHewanById(idHewan: String): Hewan {
        try {
            return hewanApiService.getHewanById(idHewan)
        } catch (e: IOException) {
            throw IOException("Failed to fetch Hewan with ID: $idHewan. Network error occurred.", e)
        }
    }
}