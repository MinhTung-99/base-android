package com.base_clean_architecture.base.adapter

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setupLoadMore(completion: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                completion()
            }
        }
    })
}