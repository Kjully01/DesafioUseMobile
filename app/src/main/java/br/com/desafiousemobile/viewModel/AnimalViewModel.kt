package br.com.desafiousemobile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.desafiousemobile.model.api.AnimalResponse
import br.com.desafiousemobile.model.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimalViewModel : ViewModel() {

    private val repository: AnimalRepository = AnimalRepository()

    private val _animalSuccess: MutableLiveData<AnimalResponse> = MutableLiveData()
    val animalSuccess: LiveData<AnimalResponse> = _animalSuccess

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

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
}