package me.intuit.cat.presentation.breed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.presentation.databinding.FragmentBreedDetailBinding
import me.intuit.cat.presentation.utils.Status

@AndroidEntryPoint
class BreedDetailFragment : Fragment() {

    private val viewModel: BreedDetailViewModel by viewModels()
    private val args : BreedDetailFragmentArgs by navArgs()
   lateinit var dataBinding: FragmentBreedDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       dataBinding = FragmentBreedDetailBinding.inflate(inflater)
        initMembers()
        observeBreedDetails()
        return dataBinding.root
    }

    private fun initMembers() {
        val breed = args.breedImage
        dataBinding.breedImage = breed
        setImages(breed)
        viewModel.loadBreedImages(
            breed.id
        )
    }

    private fun observeBreedDetails() {

    viewModel.breeds.observe(viewLifecycleOwner) { state->
        when (state.status) {
            Status.SUCCESS -> {
                state.data?.let { setBreedData(it) }
            }
            Status.LOADING -> {
            }
            Status.ERROR -> {

            }
        }

    }
}

private fun setImages(data: BreedImage) {
    if (data != null) {

        Glide.with(requireActivity())
            .load(data.url)
            .circleCrop()
            .into(dataBinding.catImage);
    }

}
private fun setBreedData(data: List<Breed>) {
    if (data != null && data.isNotEmpty()) {
        dataBinding.breed = data[0]

    }
}

}

