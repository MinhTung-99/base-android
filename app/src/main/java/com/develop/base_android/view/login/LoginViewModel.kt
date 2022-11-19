package com.develop.base_android.view.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.hideProgress
import com.develop.base_android.application.base.showProgress
import com.develop.base_android.data.network.APIRequest
import com.develop.base_android.data.network.getEntry
import com.develop.base_android.injection.gson
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(
    private val apiRequest: APIRequest
) : BaseViewModel() {

    fun getEntry() {
        viewModelScope.launch {
            showProgress()
            apiRequest.getEntry()
                .catch {

                }
                .collect {
                    hideProgress()
                    Log.d("KMSHSUPP", it.entries?.get(0)?.Description.toString())
                }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return LoginViewModel(
                    APIRequest(gson)
                ) as T
            }
        }
    }
}