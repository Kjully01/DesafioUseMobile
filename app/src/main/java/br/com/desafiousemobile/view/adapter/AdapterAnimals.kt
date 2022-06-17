package br.com.desafiousemobile.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.desafiousemobile.databinding.LayoutRecyclerViewBinding
import br.com.desafiousemobile.model.api.Animal
import coil.load

class AdapterAnimals : RecyclerView.Adapter<AdapterAnimals.ViewHolderAnimals>() {

    private var animalList : MutableList<Animal> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnimals {
        val itemBinding = LayoutRecyclerViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderAnimals(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderAnimals, position: Int) {
        holder.onBind(animalList[position])
    }

    override fun getItemCount(): Int = animalList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listAux: List<Animal>){
        animalList.clear()
        animalList.addAll(listAux)
        notifyDataSetChanged()
    }

    class ViewHolderAnimals(val binding: LayoutRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(animal: Animal){
            binding.apply {
                image.imageItem.load(animal.image)
                tvName.text = animal.name
                tvDescription.text = animal.description
            }
        }

    }
}