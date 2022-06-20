package br.com.desafiousemobile.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.desafiousemobile.R
import br.com.desafiousemobile.databinding.FragmentRegistrationBinding
import br.com.desafiousemobile.viewModel.AnimalViewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: AnimalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)
        listener()
        observer()
    }

    private fun listener() {
        binding.apply {
            btnRegistration.setOnClickListener {
                if (
                    etName.text.toString().isNotEmpty() ||
                    etDescription.text.toString().isNotEmpty() ||
                    etAge.text.toString().isNotEmpty() ||
                    etSpecies.text.toString().isNotEmpty() ||
                    etLink.text.toString().isNotEmpty()
                ) {
                    viewModel.registration(
                        etName.text.toString(),
                        etDescription.text.toString(),
                        etAge.text.toString().toInt(),
                        etSpecies.text.toString(),
                        etLink.text.toString()
                    )
                } else {
                    Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observer() {
        viewModel.apply {
            animalSuccess.observe(viewLifecycleOwner, Observer {
                clean()
            })
            error.observe(
                viewLifecycleOwner, Observer {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun clean() {
        binding.apply {
            etName.setText("")
            etDescription.setText("")
            etAge.setText("")
            etSpecies.setText("")
            etLink.setText("")
        }
    }

}