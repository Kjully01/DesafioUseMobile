package br.com.desafiousemobile.model.repository

import br.com.desafiousemobile.model.api.AnimalRequest
import br.com.desafiousemobile.model.api.AnimalResponse
import br.com.desafiousemobile.model.api.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AnimalRepository {

    private var animalRoute =
        Api("https://bootcamp-ios-api.herokuapp.com").create()

    suspend fun doTheRegistration(
        name: String,
        description: String,
        age: Int,
        species: String,
        image: String
    ): Flow<AnimalResponse> {
        return flow {
            animalRoute.doRegistration(
                AnimalRequest(
                    name,
                    description,
                    age,
                    species,
                    image
                )
            ).let { response ->
                if (response.isSuccessful) {
                    response.body()
                } else {
                    throw HttpException(response)
                }
            }?.let {
                emit(it)
            }
        }
    }

    suspend fun getTheAnimals(): Flow<AnimalResponse> {
        return flow{
            animalRoute.getAnimals()
                .let { response ->
                    if(response.isSuccessful){
                        response.body()
                    } else {
                        throw HttpException(response)
                    }
                }?.let {
                    emit(it)
                }
        }
    }

}