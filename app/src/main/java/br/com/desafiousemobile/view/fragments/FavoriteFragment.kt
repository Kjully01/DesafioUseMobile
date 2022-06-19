package br.com.desafiousemobile.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.desafiousemobile.databinding.FragmentFavoriteBinding
import br.com.desafiousemobile.model.data_local.model.Animal
import br.com.desafiousemobile.model.data_remote.model.AnimalResponse
import br.com.desafiousemobile.view.activity.DetailsActivity
import br.com.desafiousemobile.view.adapter.AdapterFavoriteAnimals
import br.com.desafiousemobile.view.adapter.FavoriteClickListener
import br.com.desafiousemobile.viewModel.AnimalViewModel

class FavoriteFragment : Fragment(), FavoriteClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapterRecyclerView: AdapterFavoriteAnimals
    private lateinit var viewModel: AnimalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)

        observer()
        startAdapter()
    }

    private fun startAdapter(){
        adapterRecyclerView = AdapterFavoriteAnimals(this)
        binding.rvFavoriteAnimals.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecyclerView
        }
    }

    private fun setDataAdapter(animals: List<Animal>){
        adapterRecyclerView.setData(animals)
    }

    private fun observer() {
        viewModel.apply {
            readAllData.observe(viewLifecycleOwner, Observer{ animal ->
                setDataAdapter(animal)
            })
        }
    }

    override fun onFavoriteClickListener(animal: Animal) {
        saveAnimal(animal)
        val intent = Intent(context, DetailsActivity::class.java)
        requireActivity().startActivity(intent)
    }

    override fun onDeleteFavoriteClickListener(animal: Animal) {
        viewModel.deleteFavoriteAnimal(animal)
    }

    private fun saveAnimal(animal: Animal){
        viewModel.saveAnimalDataStore(
            name = animal.name,
            description = animal.description,
            age = animal.age.toString().toInt(),
            species = animal.species.toString(),
            image = animal.image.toString()
        )
    }

}