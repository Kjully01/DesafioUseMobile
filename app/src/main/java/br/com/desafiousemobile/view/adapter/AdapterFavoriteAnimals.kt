package br.com.desafiousemobile.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.desafiousemobile.databinding.LayoutRecyclerViewBinding
import br.com.desafiousemobile.model.data_local.model.Animal
import br.com.desafiousemobile.model.data_remote.model.AnimalResponse
import coil.load
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

class AdapterFavoriteAnimals(
    private val onFavoriteClickListener: FavoriteClickListener
) : RecyclerView.Adapter<AdapterFavoriteAnimals.ViewHolderFavorite>() {

    private var favoriteResponseList: MutableList<Animal> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFavorite {
        val itemBinding = LayoutRecyclerViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderFavorite(itemBinding, onFavoriteClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderFavorite, position: Int) {
        holder.onBind(favoriteResponseList[position])
    }

    override fun getItemCount(): Int = favoriteResponseList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listAux: List<Animal>) {
        favoriteResponseList.clear()
        favoriteResponseList.addAll(listAux)
        notifyDataSetChanged()
    }

    class ViewHolderFavorite(
        private val binding: LayoutRecyclerViewBinding,
        private val onFavoriteClickListener: FavoriteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(animal: Animal) {
            binding.apply {
                imageItem.imageItem.load(animal.image)
                tvName.text = animal.name
                tvDescription.text = animal.description

                root.setOnClickListener {
                    onFavoriteClickListener.onFavoriteClickListener(animal)
                }

                root.icFavorite.setOnClickListener {
                    onFavoriteClickListener.onDeleteFavoriteClickListener(animal)
                }
            }
        }

    }
}