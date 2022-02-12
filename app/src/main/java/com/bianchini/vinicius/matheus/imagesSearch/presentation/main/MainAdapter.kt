package com.bianchini.vinicius.matheus.imagesSearch.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bianchini.vinicius.matheus.imagesSearch.domain.model.DataResponse

class MainAdapter : ListAdapter<DataResponse, MainViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (getItem(position).images != null) {
            holder.bind(getItem(position).images.first())
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<DataResponse>() {
            override fun areItemsTheSame(
                oldItem: DataResponse,
                newItem: DataResponse
            ): Boolean {
                return oldItem.link == newItem.link
            }

            override fun areContentsTheSame(
                oldItem: DataResponse,
                newItem: DataResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}