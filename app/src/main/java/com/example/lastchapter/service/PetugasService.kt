package com.example.lastchapter.service

import com.example.lastchapter.model.Petugas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PetugasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapetugas.php")
    suspend fun getPetugas(): List<Petugas>

    @GET("baca1petugas.php/{id_petugas}")
    suspend fun getPetugasById(@Query("id_petugas") idPetugas: String): Petugas

    @POST("insertpetugas.php")
    suspend fun insertPetugas(@Body petugas: Petugas)

    @PUT("editpetugas.php/{id_petugas}")
    suspend fun updatePetugas(@Query("id_petugas") idPetugas: String, @Body petugas: Petugas)

    @DELETE("deletepetugas.php/{id_petugas}")
    suspend fun deletePetugas(@Query("id_petugas") idPetugas: String): Response<Void>
}
