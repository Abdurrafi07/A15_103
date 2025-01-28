package com.example.lastchapter.service

import com.example.lastchapter.model.Kandang
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KandangService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacakandang.php")
    suspend fun getKandang(): List<Kandang>

    @GET("baca1kandang.php")
    suspend fun getKandangById(@Query("id_kandang") idKandang: String): Kandang

    @POST("insertkandang.php")
    suspend fun insertKandang(@Body kandang: Kandang)

    @PUT("editkandang.php")
    suspend fun updateKandang(@Query("id_kandang") idKandang: String, @Body kandang: Kandang)

    @DELETE("deletekandang.php")
    suspend fun deleteKandang(@Query("id_kandang") idKandang: String): Response<Void>
}
