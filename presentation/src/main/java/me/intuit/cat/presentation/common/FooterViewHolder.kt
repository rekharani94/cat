package me.intuit.cat.presentation.common

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import me.intuit.cat.presentation.utils.executeWithAction

/**
 *//*
class FooterViewHolder(
    private val binding: ItemPagingFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.executeWithAction {
            footerUiState = FooterUiState(loadState)
        }
    }
}

*/