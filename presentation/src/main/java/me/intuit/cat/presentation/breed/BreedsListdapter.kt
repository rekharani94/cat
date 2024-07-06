package me.intuit.cat.presentation.breed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.presentation.R
import me.intuit.cat.presentation.databinding.BreedItemBinding

import javax.inject.Inject

/**

 */

class BreedsListdapter @Inject constructor() : PagingDataAdapter<BreedImage, RecyclerView.ViewHolder>(
    DiffCallback()
) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CatItemViewHolder)?.bind(breedImage  = getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatItemViewHolder {
        return CatItemViewHolder(
            BreedItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        )
    }
    class CatItemViewHolder(private val binding: BreedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup): CatItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.breed_item, parent, false)
                val binding = BreedItemBinding.inflate(inflater)

                return CatItemViewHolder(binding)
            }
        }

        fun bind(breedImage: BreedImage?) {

            binding.ivBreedImage.load(breedImage?.url){
                placeholder(R.drawable.ic_cat)
            }
            breedImage?.breeds?.map {
                it.let {
                    binding.btnViewMore.text =  it.name
                }
            }
            binding.btnViewMore.startAnimation(AnimationUtils.loadAnimation(binding.root.context, R.anim.blink))
            binding.btnViewMore.setOnClickListener {
                breedImage?.let { image ->
                    val direction =
                        BreedsListFragmentDirections.actionBreedListToBreedDetailFragment(
                            image
                        )
                    it.findNavController().navigate(direction)
                }
                
            }
                binding.root.setOnClickListener {
                    breedImage?.let { image ->
                        val direction =
                            BreedsListFragmentDirections.actionBreedListToBreedDetailFragment(
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

