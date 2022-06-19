package br.com.desafiousemobile.model.data_remote.api

data class AnimalRequest (
    val name: String,
    val description: String,
    val age: Int,
    val species: String,
    val image: String
)