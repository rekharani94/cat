package me.intuit.cat.presentation.breed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

import kotlinx.coroutines.launch

import me.intuit.cat.presentation.base.BaseViewModel
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.util.Result
import me.intuit.cat.domain.util.onSuccess
import me.intuit.cat.presentation.utils.State

import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(var networkHelper: NetworkHelper,
) : BaseViewModel() {

    val breeds: LiveData<Result<List<Breed>>> get() = _breeds
    private val _breeds = MutableLiveData<Result<List<Breed>>>()

}
