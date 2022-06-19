package br.com.desafiousemobile.view.adapter

import br.com.desafiousemobile.model.data_local.model.Animal

interface FavoriteClickListener {

    fun onFavoriteClickListener(animal: Animal)
    fun onDeleteFavoriteClickListener(animal: Animal)

}