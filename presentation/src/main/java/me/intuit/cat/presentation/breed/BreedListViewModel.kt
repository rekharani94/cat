package me.intuit.cat.presentation.breed

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.usecase.GetBreedsFromDBUseCases
import me.intuit.cat.domain.usecase.GetBreedsListUseCase

import me.intuit.cat.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(private val getBreedsListUseCase: GetBreedsListUseCase,
                                             private val getBreedsImagesFromDBUseCase: GetBreedsFromDBUseCases,
                                             private val networkHelper: NetworkHelper
) :
    BaseViewModel() {

   private val _uiState = MutableStateFlow<PagingData<Breed>>(value = PagingData.empty())

    val uiState: StateFlow<PagingData<Breed>> = _uiState

     init {
         checkInternet()
     }

    private fun checkInternet() {
        TODO("Not yet implemented")
    }

    suspend fun fetchBreeds(): Flow<PagingData<BreedImage>> {
        return getBreedsImagesFromDBUseCase().cachedIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        //ioCoroutineScope.cancel()
    }

}