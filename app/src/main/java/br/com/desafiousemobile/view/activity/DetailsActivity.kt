package br.com.desafiousemobile.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.desafiousemobile.databinding.ActivityDetailsBinding
import br.com.desafiousemobile.viewModel.AnimalViewModel
import coil.load

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: AnimalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)

        setSupportActionBar(binding.toolbarDetails)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Detalhes"

        viewModel.readAnimalsDataStore()
        observer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun observer(){
        viewModel.apply {
            animalResponseDataSource.observe(this@DetailsActivity, Observer{
                binding.apply {
                    val info = "${it.name} - ${it.age} anos"
                    tvNameAndAge.text = info
                    tvDescription.text = it.description.toString()
                    tvSpecie.text = it.species.toString()
                    imageAnimal.load(it.image)
                }
            })
        }
    }
}