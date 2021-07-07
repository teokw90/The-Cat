package com.example.bestandroidcode.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestandroidcode.databinding.ListItemMyFavoriteBinding

class MyFavoriteAdapter: ListAdapter<String, MyFavoriteAdapter.MyFavoriteItemViewHolder>(MyFavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavoriteItemViewHolder = MyFavoriteItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: MyFavoriteItemViewHolder, position: Int) = holder.bind(getItem(position))

    class MyFavoriteItemViewHolder(private val binding: ListItemMyFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MyFavoriteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMyFavoriteBinding.inflate(layoutInflater, parent, false)

                return MyFavoriteItemViewHolder(binding)
            }
        }

        fun bind(catPhotoURL: String) {
            binding.catPhotoURL = catPhotoURL
            binding.executePendingBindings()
        }
    }
}

class MyFavoriteDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean
        = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean
        = oldItem == newItem
}