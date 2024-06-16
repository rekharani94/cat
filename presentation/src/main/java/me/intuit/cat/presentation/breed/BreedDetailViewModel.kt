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
import me.intuit.cat.domain.usecase.BreedDetailUseCases
import me.intuit.cat.domain.usecase.GetBreedDetailsFromDBUseCases
import me.intuit.cat.presentation.utils.State

import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(var networkHelper: NetworkHelper,
                                               private val breedDetailUsecase: BreedDetailUseCases,
                                               private val getbreedDetailsfromDBUseCase: GetBreedDetailsFromDBUseCases
) : BaseViewModel() {

    val breeds: LiveData<State<List<Breed>>> get() = _breeds
    private val _breeds = MutableLiveData<State<List<Breed>>>()
    fun loadBreedImages(
        imageId: String,
    ) {
        if (networkHelper.isNetworkConnected()) {

            viewModelScope.launch {

                try {
                    breedDetailUsecase(
                        imageId,
                    ).flowOn(Dispatchers.IO)
                        .catch { e ->
                            _breeds.value = State.error(e.message)

                        }.collect {
                        _breeds.value  = State.success(it)
                        }
                } catch (exception: Exception) {
                    _breeds.value = State.error(exception.message)
                }

            }
        } else {
            getbreedDetailsDirectlyFromDB()
        }

    }

    private fun getbreedDetailsDirectlyFromDB() {
        viewModelScope.launch {
            getbreedDetailsfromDBUseCase().flowOn(Dispatchers.IO)
                .catch { e ->
                    _breeds.value  = State.error(e.message)

                }.collect { it ->
                    _breeds.value  = State.success(it)

                }
        }
    }
}
