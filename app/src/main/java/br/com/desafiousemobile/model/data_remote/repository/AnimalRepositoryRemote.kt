package br.com.desafiousemobile.model.data_remote.repository

import br.com.desafiousemobile.model.data_remote.api.AnimalRequest
import br.com.desafiousemobile.model.data_remote.model.AnimalListResponse
import br.com.desafiousemobile.model.data_remote.api.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AnimalRepositoryRemote {

    private var animalRoute =
        Api("https://bootcamp-ios-api.herokuapp.com").create()

    suspend fun doTheRegistration(
        name: String,
        description: String,
        age: Int,
        species: String,
        image: String
    ): Flow<AnimalListResponse> {
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

    suspend fun getTheAnimals(): Flow<AnimalListResponse> {
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