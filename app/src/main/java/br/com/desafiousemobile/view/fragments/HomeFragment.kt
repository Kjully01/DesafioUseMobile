package br.com.desafiousemobile.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.desafiousemobile.R
import br.com.desafiousemobile.databinding.FragmentHomeBinding
import br.com.desafiousemobile.model.api.Animal
import br.com.desafiousemobile.view.activity.DetailsActivity
import br.com.desafiousemobile.view.adapter.AdapterAnimals
import br.com.desafiousemobile.view.adapter.AnimalClickListener
import br.com.desafiousemobile.viewModel.AnimalViewModel
import com.google.gson.annotations.SerializedName
import java.util.*

class HomeFragment : Fragment(), AnimalClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterRecyclerView : AdapterAnimals
    private lateinit var viewModel: AnimalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)
        viewModel.getAnimals()

        observer()
        startAdapter()
    }

    private fun startAdapter(){
        adapterRecyclerView = AdapterAnimals(this)
        binding.rvAnimals.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecyclerView
        }
    }

    private fun setDataAdapter(animalsList: List<Animal>){
       adapterRecyclerView.setData(animalsList)
    }

    override fun onAnimalClickListener(animal: Animal) {
        saveAnimal(animal)
        val intent = Intent(context, DetailsActivity::class.java)
        requireActivity().startActivity(intent)
    }

    private fun saveAnimal(animal: Animal){
        viewModel.saveAnimalDataStore(
            name = animal.name.toString(),
            description = animal.description.toString(),
            age = animal.age.toString().toInt(),
            species = animal.species.toString(),
            image = animal.image.toString()
        )
    }

    private fun observer(){
        viewModel.apply {
            animalSuccess.observe(viewLifecycleOwner, Observer{ animalResponse ->
                val animalsList = animalResponse.animals
                setDataAdapter(animalsList)
            })
            error.observe(
                viewLifecycleOwner, Observer {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

}