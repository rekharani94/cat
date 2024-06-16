package me.intuit.cat.presentation.breed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.presentation.common.FooterAdapter
import me.intuit.cat.presentation.databinding.FragmentBreedListBinding
import javax.inject.Inject
@ExperimentalPagingApi
@AndroidEntryPoint
class BreedsListFragment : Fragment() {
    lateinit var binding: FragmentBreedListBinding

    @Inject
    lateinit var adapter: BreedsListdapter
    val breedsViewModel: BreedListViewModel by viewModels()

    @Inject
    lateinit var networkHelper: NetworkHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedListBinding.inflate(inflater, container, false)
        initMembers()

        subscribeUI(adapter)

        context ?: return binding.root
        return binding.root
    }



    private fun initMembers() {
        binding.rvBreeds.layoutManager  = GridLayoutManager(context,2)
        adapter = BreedsListdapter()
        binding.rvBreeds.adapter = adapter
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding?.rvBreeds?.adapter = adapter.withLoadStateFooter(FooterAdapter(adapter::retry))

    }
    private fun subscribeUI(adapter: BreedsListdapter) {

        lifecycleScope.launch {
            breedsViewModel.fetchBreeds().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
            breedsViewModel.fetchDBstate()
            breedsViewModel.dbstate.observe(viewLifecycleOwner){
                if(it){
                    binding.progressBar.visibility=View.VISIBLE
                    binding.tvError.visibility=View.VISIBLE
                    binding.btnRetry.visibility=View.VISIBLE
                    binding.rvBreeds.visibility=View.GONE
                    binding.noInternet.visibility=View.VISIBLE
                }
            }
        }
        /*breedsViewModel.fetchBreeds().distinctUntilChanged().collectLatest {
            adapter.submitData(it)
        }*/
        breedsViewModel.networkState.observe(viewLifecycleOwner){
            if(it) {
                setUpViews()
            }
            else {
                binding.progressBar.visibility=View.VISIBLE
                binding.tvError.visibility=View.VISIBLE
                binding.btnRetry.visibility=View.VISIBLE
                binding.noInternet.visibility=View.VISIBLE
            }
        }

    }
    private fun setAdapter() {
      /*  collect(flow = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setUiState
        )*/
        //binding?.rvBreeds?.adapter = adapter.withLoadStateFooter(FooterAdapter(adapter::retry))
    }

    private fun setUiState(loadState: LoadState) {
       /* binding?.executeWithAction {
            BreedUiState(loadState)
        }*/
    }

    private fun setUpViews() {
        binding.progressBar.visibility=View.GONE
        binding.rvBreeds.visibility=View.VISIBLE
        binding.tvError.visibility=View.GONE
        binding.btnRetry.visibility=View.GONE
        binding.noInternet.visibility=View.GONE
        }


}