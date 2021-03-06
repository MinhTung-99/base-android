package com.base_clean_architecture.coffee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.base_clean_architecture.base.BaseViewModel
import com.base_clean_architecture.data.network.ApiClient
import com.base_clean_architecture.data.response.Coffee
import com.base_clean_architecture.domain.impl.RegistrationRepositoryImpl
import com.base_clean_architecture.domain.usercase.GetCoffeeUserCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffeeViewModel : BaseViewModel() {

    val coffees: LiveData<List<Coffee>>
        get() = _coffees
    private val _coffees = MutableLiveData<List<Coffee>>()

    fun getCoffee () {
        CoroutineScope(Dispatchers.IO).launch {
            val result = GetCoffeeUserCase(RegistrationRepositoryImpl(ApiClient.provideRetrofit())).invoke()
            result.onSuccess {
                _coffees.postValue(it)
            }
            result.onFailure {

            }
        }
    }
}