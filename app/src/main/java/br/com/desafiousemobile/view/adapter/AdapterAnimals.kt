package br.com.desafiousemobile.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.desafiousemobile.databinding.LayoutRecyclerViewBinding
import br.com.desafiousemobile.model.data_remote.model.AnimalResponse
import coil.load
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

class AdapterAnimals(
    private val onAnimalClickListener: AnimalClickListener
) : RecyclerView.Adapter<AdapterAnimals.ViewHolderAnimals>() {

    private var animalResponseList: MutableList<AnimalResponse> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnimals {
        val itemBinding = LayoutRecyclerViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderAnimals(itemBinding, onAnimalClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderAnimals, position: Int) {
        holder.onBind(animalResponseList[position])
    }

    override fun getItemCount(): Int = animalResponseList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listAux: List<AnimalResponse>) {
        animalResponseList.clear()
        animalResponseList.addAll(listAux)
        notifyDataSetChanged()
    }

    class ViewHolderAnimals(
        private val binding: LayoutRecyclerViewBinding,
        private val onAnimalClickListener: AnimalClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(animalResponse: AnimalResponse) {
            binding.apply {
                imageItem.imageItem.load(animalResponse.image)
                tvName.text = animalResponse.name
                tvDescription.text = animalResponse.description

                if (animalResponse.isFavorite){
                    icAnimalFavorite.visibility = View.VISIBLE
                    icFavorite.visibility = View.INVISIBLE
                }

                root.setOnClickListener {
                    onAnimalClickListener.onAnimalClickListener(animalResponse)
                }

                root.icFavorite.setOnClickListener {
                    onAnimalClickListener.onFavoriteClickListener(animalResponse)
                    icAnimalFavorite.visibility = View.VISIBLE
                    icFavorite.visibility = View.INVISIBLE
                }

                root.icAnimalFavorite.setOnClickListener {
                    onAnimalClickListener.onFavoriteClickListener(animalResponse)
                    icAnimalFavorite.visibility = View.GONE
                    icFavorite.visibility = View.VISIBLE
                }
            }
        }

    }
}