package br.com.desafiousemobile.model.data_local

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.desafiousemobile.model.data_local.model.Animal

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteAnimal(animal: Animal)

    @Update
    suspend fun updateFavoriteAnimal(animal: Animal)

    @Delete
    suspend fun deleteFavoriteAnimal(animal: Animal)

    @Query("SELECT * FROM animal_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Animal>>

}