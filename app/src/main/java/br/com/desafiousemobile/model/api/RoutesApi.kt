package br.com.desafiousemobile.model.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RoutesApi {

    @POST("/api/v1/animals")
    suspend fun doRegistration(
        @Body body: AnimalRequest
    ): Response<AnimalResponse>

}