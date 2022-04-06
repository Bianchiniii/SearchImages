package com.bianchini.vinicius.matheus.imagesSearch.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bianchini.vinicius.matheus.imagesSearch.R
import com.bianchini.vinicius.matheus.imagesSearch.databinding.ItemHolderBinding
import com.bianchini.vinicius.matheus.imagesSearch.domain.model.ImageResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException

class MainViewHolder(itemBinding: ItemHolderBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private val imageHolder = itemBinding.imageViewFirst

    fun bind(imageResponse: ImageResponse) {
        try {
            Glide.with(itemView)
                .load(imageResponse.link)
                .fallback(R.drawable.ic_img_loading_error)
                .into(imageHolder)
        } catch (e: GlideException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun create(parent: ViewGroup): MainViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemHolderBinding.inflate(inflater, parent, false)
            return MainViewHolder(itemBinding)
        }

        fun teste() {

        }
    }
}