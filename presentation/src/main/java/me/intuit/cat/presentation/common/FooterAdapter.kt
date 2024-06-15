package me.intuit.cat.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import me.intuit.cat.presentation.R
import me.intuit.cat.presentation.databinding.ItemPagingFooterBinding


class FooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterViewHolder {
        val itemPagingFooterBinding = inflate<ItemPagingFooterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_paging_footer,
            parent,
            false
        )
        return FooterViewHolder(itemPagingFooterBinding, retry)
    }

}

