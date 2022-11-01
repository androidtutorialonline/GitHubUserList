package com.app.gitHubUserList.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageFromUrl")
    fun AppCompatImageView.bindImageFromUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            // URL
            load(url)
        }
    }
