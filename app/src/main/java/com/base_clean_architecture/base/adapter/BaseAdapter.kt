package com.base_clean_architecture.base.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItem: MutableList<E?> = mutableListOf()

    private var isLoadMore = false

    fun loadMore() {
        isLoadMore = true
        listItem.add(null)
        notifyItemInserted(listItem.size - 1)
    }

    fun setupData(list: List<E>) {
        listItem.removeAll(listItem)
        listItem.addAll(list)
        notifyItemRangeInserted(listItem.size, list.size)
    }

    fun loadMore(list: List<E>) {
        listItem.addAll(list)
        notifyItemRangeInserted(listItem.size, list.size)
    }

    fun appendWhenLoadMore(list: List<E>) {
        if (isLoadMore) {
            listItem.removeLast();
            isLoadMore = false
        }

        listItem.addAll(listItem.size - 1, list)
        notifyItemRangeChanged(listItem.size - 1, list.size)
    }
}