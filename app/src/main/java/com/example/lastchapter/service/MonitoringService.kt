package com.example.lastchapter.service

import com.example.lastchapter.model.Monitoring
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MonitoringService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacamonitoring.php")
    suspend fun getMonitoring(): List<Monitoring>

    @GET("baca1monitoring.php")
    suspend fun getMonitoringById(@Query("id_monitoring") idMonitoring: String): Monitoring

    @POST("insertmonitoring.php")
    suspend fun insertMonitoring(@Body monitoring: Monitoring)

    @PUT("editmonitoring.php")
    suspend fun updateMonitoring(@Query("id_monitoring") idMonitoring: String, @Body monitoring: Monitoring)

    @DELETE("deletemonitoring.php")
    suspend fun deleteMonitoring(@Query("id_monitoring") idMonitoring: String): Response<Void>
}

