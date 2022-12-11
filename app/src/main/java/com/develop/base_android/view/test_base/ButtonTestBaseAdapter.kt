package com.develop.base_android.view.test_base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.develop.base_android.application.base.BaseAdapter
import com.develop.base_android.databinding.ItemButtonTestBaseBinding
import com.develop.base_android.resource.utils.setOnSingleClick

class ButtonTestBaseAdapter(
    private val callBack: (pos: Int, view: View) -> Unit
) : BaseAdapter<ButtonTestBaseModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemButtonTestBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ButtonTestBaseViewHolder(binding)

        binding.btnTestBase.setOnSingleClick {
            callBack.invoke(viewHolder.adapterPosition, binding.root)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ButtonTestBaseViewHolder).binding.btnTestBase.text = listItem[position]?.title
    }

    override fun getItemCount(): Int = listItem.size

    class ButtonTestBaseViewHolder(val binding: ItemButtonTestBaseBinding) : RecyclerView.ViewHolder(binding.root)
}