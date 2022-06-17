package br.com.desafiousemobile.model.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class AnimalResponse (
    @SerializedName("items")
    val animals: List<Animal>
)

data class Animal(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("created_at")
    val created_at: Date?,
    @SerializedName("updated_at")
    val updated_at: Date?
)

