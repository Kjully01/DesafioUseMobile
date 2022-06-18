package br.com.desafiousemobile.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.desafiousemobile.databinding.LayoutRecyclerViewBinding
import br.com.desafiousemobile.model.api.Animal
import coil.load

class AdapterAnimals(
    private val onAnimalClickListener: AnimalClickListener
) : RecyclerView.Adapter<AdapterAnimals.ViewHolderAnimals>() {

    private var animalList: MutableList<Animal> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnimals {
        val itemBinding = LayoutRecyclerViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderAnimals(itemBinding, onAnimalClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderAnimals, position: Int) {
        holder.onBind(animalList[position])
    }

    override fun getItemCount(): Int = animalList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listAux: List<Animal>) {
        animalList.clear()
        animalList.addAll(listAux)
        notifyDataSetChanged()
    }

    class ViewHolderAnimals(
        private val binding: LayoutRecyclerViewBinding,
        private val onAnimalClickListener: AnimalClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(animal: Animal) {
            binding.apply {
                imageItem.imageItem.load(animal.image)
                tvName.text = animal.name
                tvDescription.text = animal.description

                root.setOnClickListener {
                    onAnimalClickListener.onAnimalClickListener(animal)
                }
            }
        }

    }
}