package me.intuit.cat.presentation.breed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.intuit.cat.data.utils.AppConstant
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.data.utils.generic.ConstantsErrorHandler
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.presentation.base.UiState
import me.intuit.cat.presentation.common.BreedUiState
import me.intuit.cat.presentation.common.FooterAdapter
import me.intuit.cat.presentation.databinding.FragmentBreedListBinding
import me.intuit.cat.presentation.utils.ErrorConstants
import me.intuit.cat.presentation.utils.collect
import me.intuit.cat.presentation.utils.executeWithAction
import java.util.Collections
import java.util.Random

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

    lateinit var list:PagingData<BreedImage>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedListBinding.inflate(inflater, container, false)
        initMembers()
        setListener()

        subscribeUI(adapter)

        context ?: return binding.root
        return binding.root
    }



    private fun initMembers() {

        binding.rvBreeds.layoutManager  = GridLayoutManager(context,2)
        adapter = BreedsListdapter()
        binding.rvBreeds.adapter = adapter
        setAdapter()
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding?.rvBreeds?.adapter = adapter.withLoadStateFooter(FooterAdapter(adapter::retry))
       /* binding.container.setOnRefreshListener {

            // on below line we are setting is refreshing to false.
            binding.container.isRefreshing = false

            // on below line we are shuffling our list using ra
            // on below line we are notifying adapter
            // that data has changed in recycler view.
            adapter.refresh()
        }*/
    }
    private fun subscribeUI(adapter: BreedsListdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                breedsViewModel.uiState.collectLatest { state ->
                    when (state) {
                        is UiState.Error -> {
                            handleErrorUI(state.message)
                        }

                        is UiState.Loading -> state.toString()
                        is UiState.Success -> {
                            list = state.data
                            adapter.submitData(state.data)
                            binding.imgNoDatafound.visibility = View.GONE
                            binding.imgNoInternet.visibility = View.GONE
                        }
                    }
                }


            }
        }

       /* lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
               binding.btnRetry.isVisible = loadStates.refresh !is LoadState.Loading
                binding.tvError.isVisible = loadStates.refresh is LoadState.Error
            }
        }*/


    }

    private fun handleErrorUI(message: String) {
        when (message) {
            ErrorConstants.EXCEPTION_MESSAGE -> {
                binding.imgNoDatafound.visibility = View.VISIBLE

            }

            ErrorConstants.NO_CONNECTION_INTERNET_MESSAGE -> {
                binding.imgNoInternet.visibility = View.VISIBLE
            }


            ErrorConstants.NO_DATA_AVAILABLE_IN_DB -> {
                binding.imgNoDatafound.visibility = View.VISIBLE
            }
        }
    }


    private fun setListener() {
        binding.btnRetry.setOnClickListener { adapter.retry() }
    }
    private fun setAdapter() {
        collect(flow = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setUsersUiState
        )
        binding.rvBreeds.adapter = adapter.withLoadStateHeaderAndFooter(
                header = FooterAdapter(adapter::retry),
                footer = FooterAdapter(adapter::retry)
            )
    }

    private fun setUsersUiState(loadState: LoadState) {
        binding.executeWithAction {
            uiState = BreedUiState(loadState)
        }
    }
}
/*lifecycleScope.launch {
            breedsViewModel.networkState.observe(viewLifecycleOwner) { isOnline ->
                // binding.progressBar.visibility = if (!isOnline) View.VISIBLE else View.GONE
                // binding.imgNoInternet.visibility = if (!isOnline) View.VISIBLE else View.GONE
                binding.noInternet.visibility = if (!isOnline) View.VISIBLE else View.GONE

                *//*binding.btnRetry.visibility = if (!isOnline) View.VISIBLE else View.GONE
                binding.rvBreeds.visibility = if (!isOnline) View.GONE else View.VISIBLE
                binding.noInternet.visibility = if (!isOnline) View.VISIBLE else View.GONE*//*

            }*/