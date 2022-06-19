package br.com.desafiousemobile.model.data_local.repository

import androidx.lifecycle.LiveData
import br.com.desafiousemobile.model.data_local.AnimalDao
import br.com.desafiousemobile.model.data_local.model.Animal

class AnimalRepositoryLocal(private val animalDao: AnimalDao) {
    val readAllData: LiveData<List<Animal>> = animalDao.readAllData()

    suspend fun addFavoriteAnimal(animal: Animal){
        animalDao.addFavoriteAnimal(animal)
    }

    suspend fun updateFavoriteAnimal(animal: Animal){
        animalDao.updateFavoriteAnimal(animal)
    }

    suspend fun deleteFavoriteAnimal(animal: Animal){
        animalDao.deleteFavoriteAnimal(animal)
    }
}