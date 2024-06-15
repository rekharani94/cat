package me.intuit.cat.presentation.utils

import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> T.executeWithAction(action: T.() -> Unit) {
    action()
    executePendingBindings()
}
