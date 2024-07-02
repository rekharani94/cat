package me.intuit.cat.presentation.breed

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.getResult
import me.intuit.cat.presentation.databinding.FragmentBreedDetailBinding

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
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true);
       /*dataBinding.attriLayout.setOnClickListener {
           animate(true)
       }*/
        initMembers()
        return dataBinding.root
    }

    private fun initMembers() {
        val breed = args.breedImage
        dataBinding.breedImage = breed
        setImages(breed)
        setBreedData(breed.breeds)

    }

    private fun observeBreedDetails() {
        viewModel.breeds.observe(viewLifecycleOwner) { state ->
            state.getResult({
                setBreedData(it.data)

            },{
                dataBinding.progressBar.visibility = View.VISIBLE
                dataBinding.noDataAvailable.visibility = View.VISIBLE
            })

           /* dataBinding.noDataAvailable.visibility = when (state.status) {
                Status.ERROR -> View.VISIBLE
                else -> View.GONE
            }

            state.data?.let {
                if (state.status == Status.SUCCESS) {
                }
            }*/
        }
  /*  viewModel.breeds.observe(viewLifecycleOwner) { state->
        when (state.status) {
            Status.SUCCESS -> {
                state.data?.let { setBreedData(it) }
                dataBinding.progressBar.visibility = View.GONE

            }
            Status.LOADING -> {
                dataBinding.progressBar.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                dataBinding.noDataAvailable.visibility = View.VISIBLE
            }
        }

    }*/
}

    private fun setImages(breedImage: BreedImage?) {
        breedImage?.let {
            Glide.with(requireActivity()).load(it.url)
                .circleCrop()
                .into(dataBinding.catImage)

        }
    }

private fun setBreedData(data: List<Breed>) {
    data.firstOrNull()?.let {
        dataBinding.breed = it
    }
}

    private fun animate(reverse: Boolean = false) {
        val colorTo = if (!reverse) Color.WHITE else Color.TRANSPARENT

        ObjectAnimator.ofArgb(dataBinding.attriLayout, "strokeColor", colorTo).apply {
            duration = 300
            addUpdateListener {
                dataBinding.attriLayout.invalidate()
            }

            if (!reverse) {
                doOnEnd {
                    animate(reverse = true)
                }
            }
            start()
        }
    }

}

