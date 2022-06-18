package br.com.desafiousemobile.view.fragments

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
import br.com.desafiousemobile.model.api.Animal
import br.com.desafiousemobile.view.adapter.AdapterAnimals
import br.com.desafiousemobile.viewModel.AnimalViewModel
import coil.load
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterRecyclerView : AdapterAnimals
    private lateinit var viewModel: AnimalViewModel

    //private var animalsList: List<Animal> = listOf()

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
        binding.rvAnimals.apply {
            adapterRecyclerView = AdapterAnimals()
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecyclerView
        }
    }

    private fun setDataAdapter(animalsList: List<Animal>){
       adapterRecyclerView.setData(animalsList)
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