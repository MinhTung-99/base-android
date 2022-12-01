package com.develop.base_android.view.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.develop.base_android.application.base.BaseSingleAdapter
import com.develop.base_android.application.base.BaseViewHolder
import com.develop.base_android.data.local.ItemMenu
import com.develop.base_android.databinding.ItemMenuBinding
import com.develop.base_android.resource.utils.setOnSingleClick

class ItemMenuAdapter(
    private val context: Context,
    private val onClickOption: (model: ItemMenu) -> Unit
) :
    BaseSingleAdapter<ItemMenu, ItemMenuBinding>() {
    override fun createViewBinding(parent: ViewGroup, viewType: Int): ItemMenuBinding =
        ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


    override fun bindingViewHolder(holder: BaseViewHolder<ItemMenuBinding>, position: Int) {
        listItem[position]?.icon?.let { holder.binding.imgMenu.setImageResource(it) }
        holder.binding.nameItem.text = listItem[position]?.content?.let { context.getString(it) }
    }

    override fun createViewHolder(binding: ItemMenuBinding): BaseViewHolder<ItemMenuBinding> {
        val holder = BaseViewHolder(binding)

        holder.binding.root.setOnSingleClick {
            listItem[holder.adapterPosition]?.let { onClickOption.invoke(it) }
        }
        return holder
    }


}