package br.com.desafiousemobile.viewModel

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.desafiousemobile.model.api.Animal
import br.com.desafiousemobile.model.api.AnimalResponse
import br.com.desafiousemobile.model.dataStore.DataStoreManager
import br.com.desafiousemobile.model.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AnimalViewModel : ViewModel() {

    private val repository: AnimalRepository = AnimalRepository()

    private val _animalSuccess: MutableLiveData<AnimalResponse> = MutableLiveData()
    val animalSuccess: LiveData<AnimalResponse> = _animalSuccess

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _animalDataSource: MutableLiveData<Animal> = MutableLiveData()
    val animalDataSource: LiveData<Animal> get() = _animalDataSource

    fun registration(
        name: String,
        description: String,
        age: Int,
        species: String,
        image: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.doTheRegistration(
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
            repository.getTheAnimals()
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

        _animalDataSource.postValue(
            Animal(
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
}