package com.base_clean_architecture.coffee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base_clean_architecture.base.BaseAdapter
import com.base_clean_architecture.data.response.Coffee
import com.base_clean_architecture.databinding.ItemCoffeeBinding

class CoffeeAdapter : BaseAdapter<CoffeeAdapter.CoffeeViewHolder, Coffee, ItemCoffeeBinding>() {

    class CoffeeViewHolder(itemView: ItemCoffeeBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    override fun createViewBinding(parent: ViewGroup, viewType: Int): ItemCoffeeBinding {
        return ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun createViewHolder(binding: ItemCoffeeBinding): CoffeeViewHolder {
        return CoffeeViewHolder(binding)
    }

    override fun bindingViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.binding.txtCoffee.text = listItem[position]?.title
    }
}