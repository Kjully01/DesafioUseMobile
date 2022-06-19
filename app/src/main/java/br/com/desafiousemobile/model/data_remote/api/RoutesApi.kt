package br.com.desafiousemobile.model.data_remote.api

import br.com.desafiousemobile.model.data_remote.model.AnimalListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RoutesApi {

    @POST("/api/v1/animals")
    suspend fun doRegistration(
        @Body body: AnimalRequest
    ): Response<AnimalListResponse>

    @GET("/api/v1/animals")
    suspend fun getAnimals(): Response<AnimalListResponse>

}