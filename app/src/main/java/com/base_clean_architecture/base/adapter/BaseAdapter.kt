package com.base_clean_architecture.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.base_clean_architecture.databinding.ItemLoadMoreBinding

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, E, B : ViewBinding> :
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

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun getItemViewType(position: Int): Int {
        return if (listItem[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    abstract fun createViewBinding(parent: ViewGroup, viewType: Int): B
    abstract fun createViewHolder(binding: B): VH
    abstract fun bindingViewHolder(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = createViewBinding(parent, viewType)
                createViewHolder(binding = binding)
            }

            else -> {
                val binding = ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is LoadingViewHolder) {
            bindingViewHolder(holder as VH, position)
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class LoadingViewHolder(itemView: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }
}