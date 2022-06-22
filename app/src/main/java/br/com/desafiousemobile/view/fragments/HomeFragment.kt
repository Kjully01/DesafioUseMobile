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
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.desafiousemobile.databinding.FragmentHomeBinding
import br.com.desafiousemobile.model.data_remote.model.AnimalResponse
import br.com.desafiousemobile.view.activity.DetailsActivity
import br.com.desafiousemobile.view.adapter.AdapterAnimals
import br.com.desafiousemobile.view.adapter.AnimalClickListener
import br.com.desafiousemobile.viewModel.AnimalViewModel

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

    private fun setDataAdapter(animalsList: List<AnimalResponse>){
       adapterRecyclerView.setData(animalsList)
    }

    override fun onAnimalClickListener(animalResponse: AnimalResponse) {
        saveAnimal(animalResponse)
        val intent = Intent(context, DetailsActivity::class.java)
        requireActivity().startActivity(intent)
    }

    override fun onFavoriteClickListener(animalResponse: AnimalResponse) {
        onFavoriteAnimal(animalResponse)
    }

    private fun saveAnimal(animalResponse: AnimalResponse){
        if(animalResponse.name?.isNotEmpty() == true){
            viewModel.saveAnimalDataStore(
                name = animalResponse.name.toString(),
                description = animalResponse.description.toString(),
                age = animalResponse.age.toString().toInt(),
                species = animalResponse.species.toString(),
                image = animalResponse.image.toString()
            )
        } else {
            Toast.makeText(requireContext(), "Erro ao selecionar esse animal. Abrindo o último escolhido", Toast.LENGTH_LONG).show()
        }
    }

    private fun onFavoriteAnimal(animalResponse: AnimalResponse){
        if(animalResponse.name?.isNotEmpty() == true){
            viewModel.clickOnFavoriteAnimal(animalResponse)
        } else {
            Toast.makeText(requireContext(), "Erro ao favoritar esse animal", Toast.LENGTH_LONG).show()
        }

    }

    private fun observer(){
        viewModel.apply {
            animalSuccess.observe(viewLifecycleOwner, Observer{ animalResponse ->
                val animalsList = animalResponse.animalResponses
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