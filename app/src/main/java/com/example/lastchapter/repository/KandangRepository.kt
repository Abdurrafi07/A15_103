package com.example.lastchapter.repository

import com.example.lastchapter.model.Kandang
import com.example.lastchapter.service.KandangService
import java.io.IOException

interface KandangRepository {
    suspend fun getKandang(): List<Kandang>
    suspend fun insertKandang(kandang: Kandang)
    suspend fun updateKandang(idKandang: String, kandang: Kandang)
    suspend fun deleteKandang(idKandang: String)
    suspend fun getKandangById(idKandang: String): Kandang
}

class NetworkKandangRepository(
    private val kandangApiService: KandangService
) : KandangRepository {
    override suspend fun getKandang(): List<Kandang> =
        kandangApiService.getKandang()

    override suspend fun insertKandang(kandang: Kandang) {
        kandangApiService.insertKandang(kandang)
    }

    override suspend fun updateKandang(idKandang: String, kandang: Kandang) {
        kandangApiService.updateKandang(idKandang, kandang)
    }

    override suspend fun deleteKandang(idKandang: String) {
        try {
            val response = kandangApiService.deleteKandang(idKandang)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Kandang. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKandangById(idKandang: String): Kandang {
        try {
            return kandangApiService.getKandangById(idKandang)
        } catch (e: IOException) {
            throw IOException("Failed to fetch Kandang with ID: $idKandang. Network error occurred.", e)
        }
    }

}