package br.com.desafiousemobile.model.data_local

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.desafiousemobile.model.data_local.model.Animal

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteAnimal(animal: Animal)

    @Delete
    suspend fun deleteFavoriteAnimal(animal: Animal)

    @Query("SELECT id FROM animal_table WHERE id = :id")
    fun searchAnimal(id: String): String

    @Query("SELECT * FROM animal_table ORDER BY id DESC")
    suspend fun listAllAnimals(): List<Animal>

    @Query("SELECT * FROM animal_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Animal>>

}