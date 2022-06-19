package br.com.desafiousemobile.model.data_local.repository

import androidx.lifecycle.LiveData
import br.com.desafiousemobile.model.data_local.AnimalDao
import br.com.desafiousemobile.model.data_local.model.Animal

class AnimalRepositoryLocal(private val animalDao: AnimalDao) {
    val readAllData: LiveData<List<Animal>> = animalDao.readAllData()

    suspend fun addFavoriteAnimal(animal: Animal) {
        animalDao.addFavoriteAnimal(animal)
    }

    fun searchAnimal(id: String): String {
        return animalDao.searchAnimal(id)
    }

    suspend fun deleteFavoriteAnimal(animal: Animal) {
        animalDao.deleteFavoriteAnimal(animal)
    }
}