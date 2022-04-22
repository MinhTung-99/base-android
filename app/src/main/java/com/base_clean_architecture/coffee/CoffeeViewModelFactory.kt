package com.base_clean_architecture.coffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoffeeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(CoffeeViewModel()) as T
    }
}