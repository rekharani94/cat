package me.intuit.cat.presentation.breed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.intuit.cat.domain.model.BreedImage

//import me.amitshekhar.newsapp.presentation.databinding.ItemBreedImageViewBinding
import javax.inject.Inject

/**
 * Adapter for the [RecyclerView] in [NewsFragment].
 */
/*
class BreedsListdapter @Inject constructor() : PagingDataAdapter<BreedImage, RecyclerView.ViewHolder>(
    DiffCallback()
) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CatItemViewHolder)?.bind(breedImage  = getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatItemViewHolder {
        return CatItemViewHolder(
            ItemBreedImageViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        )
    }
    */
/**
     * view holder class for doggo item
     *//*

    class CatItemViewHolder(private val binding: ItemBreedImageViewBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup): CatItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.cat_image_item, parent, false)
                val binding = ItemBreedImageViewBinding.inflate(inflater)

                return CatItemViewHolder(binding)
            }
        }

        fun bind(breedImage: BreedImage?) {

            binding.ivDoggoMain.load(breedImage?.url){
                placeholder(R.drawable.ic_cat)
            }
            binding.root.setOnClickListener {
                breedImage?.let { image ->
                    val direction=  BreedsListFragmentDirections.actionCatListFragmentToBreedDetailFragment(
                          image
                      )
                    it.findNavController().navigate(direction)

                }
            }

        }

    }


        private class DiffCallback : DiffUtil.ItemCallback<BreedImage>() {


        override fun areItemsTheSame(
            oldItem: BreedImage,
            newItem: BreedImage
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BreedImage,
            newItem: BreedImage
        ): Boolean {
            return oldItem == newItem
        }
    }
}
*/
