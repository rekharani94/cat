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
import me.intuit.cat.presentation.databinding.FragmentBreedListBinding
import me.intuit.cat.presentation.utils.collect
import javax.inject.Inject
@ExperimentalPagingApi
@AndroidEntryPoint
class BreedsListFragment : Fragment() {
    lateinit var binding: FragmentBreedListBinding

    //@Inject
    //lateinit var adapter: BreedsListdapter
    val newsViewModel: BreedListViewModel by viewModels()

    @Inject
    lateinit var networkHelper: NetworkHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        //setAdapter()
       // setUpViews(view)
        // subscribeUI(adapter)

        //subscribeUI(adapter)
    }

    private fun initMembers() {
       // adapter = BreedsListdapter()
        binding.rvBreeds.layoutManager  = GridLayoutManager(context, 2)
       // binding.rvBreeds.adapter = adapter
        //adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY

    }
}