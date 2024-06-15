package me.intuit.cat.presentation.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by Rekha on 11/11/2021.
 */

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(this).load(url).into(this)
}
