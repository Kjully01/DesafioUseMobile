package br.com.desafiousemobile.viewModel

import android.app.Application
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.*
import br.com.desafiousemobile.model.data_local.AnimalDatabase
import br.com.desafiousemobile.model.data_remote.model.AnimalResponse
import br.com.desafiousemobile.model.data_remote.model.AnimalListResponse
import br.com.desafiousemobile.model.data_local.dataStore.DataStoreManager
import br.com.desafiousemobile.model.data_local.model.Animal
import br.com.desafiousemobile.model.data_local.repository.AnimalRepositoryLocal
import br.com.desafiousemobile.model.data_remote.repository.AnimalRepositoryRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AnimalViewModel(application: Application) : AndroidViewModel(application) {

    var readAllData: LiveData<List<Animal>>

    private val repositoryRemote: AnimalRepositoryRemote = AnimalRepositoryRemote()
    private var repositoryLocal: AnimalRepositoryLocal

    private val _animalSuccess: MutableLiveData<AnimalListResponse> = MutableLiveData()
    val animalSuccess: LiveData<AnimalListResponse> = _animalSuccess

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _animalResponseDataSource: MutableLiveData<AnimalResponse> = MutableLiveData()
    val animalResponseDataSource: LiveData<AnimalResponse> get() = _animalResponseDataSource

    init {
        val animalDao = AnimalDatabase.getDatabase(
            application
        ).animalDao()
        repositoryLocal = AnimalRepositoryLocal(animalDao)
        readAllData = repositoryLocal.readAllData
    }

    fun registration(
        name: String,
        description: String,
        age: Int,
        species: String,
        image: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRemote.doTheRegistration(
                name,
                description,
                age,
                species,
                image
            ).catch { exception ->
                _error.postValue(exception.message)
            }.collect { animalResponse ->
                _animalSuccess.postValue(animalResponse)
            }
        }
    }

    fun getAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRemote.getTheAnimals()
                .catch { exception ->
                    _error.postValue(exception.message)
                }.collect {
                    _animalSuccess.postValue(it)
                }
        }
    }

    fun saveAnimalDataStore(
        name: String,
        description: String,
        age: Int,
        species: String,
        image: String
    ) {
        runBlocking {
            DataStoreManager.setDataStore(
                preferencesKey = stringPreferencesKey("NAME"),
                value = name
            )
            DataStoreManager.setDataStore(
                preferencesKey = stringPreferencesKey("DESCRIPTION"),
                value = description
            )
            DataStoreManager.setDataStore(
                preferencesKey = intPreferencesKey("AGE"),
                value = age
            )
            DataStoreManager.setDataStore(
                preferencesKey = stringPreferencesKey("SPECIES"),
                value = species
            )
            DataStoreManager.setDataStore(
                preferencesKey = stringPreferencesKey("IMAGE"),
                value = image
            )
        }
    }

    fun readAnimalsDataStore() {
        var animalNameDataStore: String?
        var animalDescription: String?
        var animalAge: Int?
        var animalSpecies: String?
        var animalImage: String?

        runBlocking {
            animalNameDataStore = DataStoreManager.readDataStore(stringPreferencesKey("NAME"))
            animalDescription = DataStoreManager.readDataStore(stringPreferencesKey("DESCRIPTION"))
            animalAge = DataStoreManager.readDataStore(intPreferencesKey("AGE"))
            animalSpecies = DataStoreManager.readDataStore(stringPreferencesKey("SPECIES"))
            animalImage = DataStoreManager.readDataStore(stringPreferencesKey("IMAGE"))
        }

        _animalResponseDataSource.postValue(
            AnimalResponse(
                id = null,
                name = animalNameDataStore,
                description = animalDescription,
                age = animalAge,
                species = animalSpecies,
                image = animalImage,
                created_at = null,
                updated_at = null
            )
        )
    }

    fun clickOnFavoriteAnimal(animalResponse: AnimalResponse) {
        val animal = Animal(
            id = animalResponse.id.toString(),
            name = animalResponse.name.toString(),
            description = animalResponse.description.toString(),
            age = animalResponse.age.toString().toInt(),
            species = animalResponse.species.toString(),
            image = animalResponse.image.toString()
        )

        addOrDeleteAnimal(animal)
    }

    private fun addOrDeleteAnimal(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            var idAnimal = repositoryLocal.searchAnimal(animal.id)
            if(animal.id == idAnimal){
                deleteFavoriteAnimal(animal)
            } else {
                addFavoriteAnimal(animal)
            }
        }
    }

    private fun addFavoriteAnimal(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryLocal.addFavoriteAnimal(animal)
        }
    }

    fun deleteFavoriteAnimal(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryLocal.deleteFavoriteAnimal(animal)
        }
    }
}