package me.intuit.cat.presentation.breed

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.intuit.cat.data.utils.DefaultNetworkHelper
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.data.worker.WorkerStarter
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.usecase.GetBreedsFromDBUseCases
import me.intuit.cat.domain.usecase.GetBreedsListUseCase

import me.intuit.cat.presentation.base.BaseViewModel
import me.intuit.cat.presentation.base.UiState
import me.intuit.cat.presentation.synchonization.SyncWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(
    private var workerStarter: WorkerStarter,
    private val getBreedsListUseCase: GetBreedsListUseCase,
    private val getBreedsImagesFromDBUseCase: GetBreedsFromDBUseCases,
    private val networkHelper: NetworkHelper
) :
    BaseViewModel() {


 private val _uiState = MutableStateFlow<UiState<PagingData<BreedImage>>>(UiState.Loading)

    val uiState: StateFlow<UiState<PagingData<BreedImage>>> = _uiState
     var _networkState = MutableLiveData<Boolean>(false)

    var networkState: LiveData<Boolean> = _networkState
    var _dbstate = MutableLiveData<Boolean>(false)

    var dbstate: LiveData<Boolean> = _dbstate

     init {
         checkInternet()
         //fetchBreeds()

     }


    private fun checkInternet() {
        networkState=(networkHelper as DefaultNetworkHelper).isConnected.asLiveData()
        if(networkState.value==true) {
            workerStarter()
            fetchBreeds()

        }
        else {
            fetchBreedsfromDB()

        }

    }

     fun fetchBreeds() {
        try {
            viewModelScope.launch {
                getBreedsImagesFromDBUseCase().distinctUntilChanged().cachedIn(viewModelScope)
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }.collect{
                       _uiState.value = UiState.Success(it)
                    }
            }

        }
        catch (e:Exception){

        }

    }

    fun fetchBreedsfromDB() {
        viewModelScope.launch {
            getBreedsListUseCase(20,0).distinctUntilChanged().cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect{
                   _uiState.value = UiState.Success(it)
                }
        }


    }



}