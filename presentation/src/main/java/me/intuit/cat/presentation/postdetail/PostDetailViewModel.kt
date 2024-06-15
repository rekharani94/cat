package me.intuit.cat.presentation.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.intuit.cat.domain.model.Article
import me.intuit.cat.domain.usecase.PostDetailUseCases
import me.intuit.cat.presentation.base.UiState
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(private val postDetailUseCases: PostDetailUseCases) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun fetchPost() {
        viewModelScope.launch {
            postDetailUseCases.getPostUseCase()
            // ...
            // ...
        }
    }

    fun fetchComment() {
        viewModelScope.launch {
            postDetailUseCases.getCommentUseCase()
            // ...
            // ...
        }
    }

}