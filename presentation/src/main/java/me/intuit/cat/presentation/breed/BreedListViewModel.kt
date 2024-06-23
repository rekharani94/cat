package me.intuit.cat.presentation.breed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.intuit.cat.data.utils.DefaultNetworkHelper
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.usecase.GetBreedsFromDBUseCases
import me.intuit.cat.domain.usecase.GetBreedsListUseCase

import me.intuit.cat.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(
                                             private val getBreedsImagesFromDBUseCase: GetBreedsFromDBUseCases,
                                             private val networkHelper: NetworkHelper
) :
    BaseViewModel() {

   private val _uiState = MutableStateFlow<PagingData<Breed>>(value = PagingData.empty())

    val uiState: StateFlow<PagingData<Breed>> = _uiState
     var _networkState = MutableLiveData<Boolean>(false)

    var networkState: LiveData<Boolean> = _networkState
    var _dbstate = MutableLiveData<Boolean>(false)

    var dbstate: LiveData<Boolean> = _dbstate

     init {
         checkInternet()
     }

    private fun checkInternet() {
        networkState= (networkHelper as DefaultNetworkHelper).isConnected.asLiveData()

    }

    suspend fun fetchBreeds(): Flow<PagingData<BreedImage>> {
        try {
            return getBreedsImagesFromDBUseCase().cachedIn(viewModelScope)

        }
        catch (e:Exception){

        }
        return getBreedsImagesFromDBUseCase().cachedIn(viewModelScope)

    }

    suspend fun fetchDBstate() {
        try {
            var result = getBreedsImagesFromDBUseCase().cachedIn(viewModelScope)
            _dbstate.value = false
        }
        catch(_:Exception) {
            _dbstate.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        //ioCoroutineScope.cancel()
    }

}