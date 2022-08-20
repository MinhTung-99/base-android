package com.base_clean_architecture.coffee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base_clean_architecture.base.adapter.BaseAdapter
import com.base_clean_architecture.data.response.Coffee
import com.base_clean_architecture.databinding.ItemCoffeeBinding
import com.base_clean_architecture.databinding.ItemLoadMoreBinding

class CoffeeAdapter : BaseAdapter<Coffee>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding =
                    ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CoffeeViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CoffeeViewHolderLoadMore(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_ITEM -> {
                val itemViewHolder = holder as CoffeeViewHolder
                itemViewHolder.binding.txtCoffee.text = listItem[position]?.title
            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItem[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    class CoffeeViewHolder(itemView: ItemCoffeeBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    class CoffeeViewHolderLoadMore(itemView: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }
}