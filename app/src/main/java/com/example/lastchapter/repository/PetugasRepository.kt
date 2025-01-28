package com.example.lastchapter.repository

import com.example.lastchapter.model.Petugas
import com.example.lastchapter.service.PetugasService
import java.io.IOException

interface PetugasRepository {
    suspend fun getPetugas(): List<Petugas>
    suspend fun insertPetugas(petugas: Petugas)
    suspend fun updatePetugas(idPetugas: String, petugas: Petugas)
    suspend fun deletePetugas(idPetugas: String)
    suspend fun getPetugasById(idPetugas: String): Petugas
}

class NetworkPetugasRepository(
    private val petugasApiService: PetugasService
) : PetugasRepository {
    override suspend fun getPetugas(): List<Petugas> =
        petugasApiService.getPetugas()

    override suspend fun insertPetugas(petugas: Petugas) {
        petugasApiService.insertPetugas(petugas)
    }

    override suspend fun updatePetugas(idPetugas: String, petugas: Petugas) {
        petugasApiService.updatePetugas(idPetugas, petugas)
    }

    override suspend fun deletePetugas(idPetugas: String) {
        try {
            val response = petugasApiService.deletePetugas(idPetugas)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Petugas. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPetugasById(idPetugas: String): Petugas {
        try {
            return petugasApiService.getPetugasById(idPetugas)
        } catch (e: IOException) {
            throw IOException("Failed to fetch Petugas with ID: $idPetugas. Network error occurred.", e)
        }
    }

}