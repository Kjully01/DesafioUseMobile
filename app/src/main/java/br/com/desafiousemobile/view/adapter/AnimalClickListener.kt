package br.com.desafiousemobile.view.adapter

import br.com.desafiousemobile.model.data_remote.model.AnimalResponse

interface AnimalClickListener {

    fun onAnimalClickListener(animalResponse: AnimalResponse)
    fun onFavoriteClickListener(animalResponse: AnimalResponse)

}