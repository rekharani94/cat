package me.intuit.cat.presentation.common

import android.content.Context
import androidx.paging.LoadState
import me.amitshekhar.newsapp.presentation.R
import me.intuit.cat.presentation.common.BaseUiState


/**
 * Created by Rekha
 */
data class BreedUiState(
    private val loadState: LoadState
) : BaseUiState() {

    fun getProgressBarVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getListVisibility() = getViewVisibility(isVisible = loadState is LoadState.NotLoading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun getErrorMessage(context: Context) = if (loadState is LoadState.Error) {
        loadState.error.localizedMessage ?: context.getString(R.string.something_went_wrong)
    } else ""
}