package com.develop.base_android.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.develop.base_android.application.base.BaseAdapter
import com.develop.base_android.databinding.ItemBottomSheetBinding
import com.develop.base_android.resource.utils.setOnSingleClick

class BottomSheetAdapter(
    private val titles: List<ItemBottomSheet>,
    private val clickItem: (model: ItemBottomSheet) -> Unit
): BaseAdapter<ItemBottomSheetBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = BottomSheetViewHolder(binding)

        viewHolder.binding.txtTitle.setOnSingleClick {
            clickItem.invoke(titles[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BottomSheetViewHolder).binding.txtTitle.text = titles[position].title
    }

    override fun getItemCount(): Int = titles.size

    class BottomSheetViewHolder(var binding: ItemBottomSheetBinding) : RecyclerView.ViewHolder(binding.root)
}