package br.com.desafiousemobile.model.data_remote.model

import com.google.gson.annotations.SerializedName

data class AnimalListResponse (
    @SerializedName("items")
    val animalResponses: List<AnimalResponse>
)