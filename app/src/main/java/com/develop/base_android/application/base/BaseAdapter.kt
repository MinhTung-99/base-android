package com.develop.base_android.application.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItem: MutableList<E?> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpData(data: List<E>) {
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }
}