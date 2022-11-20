package com.develop.base_android.view.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.hideProgress
import com.develop.base_android.application.base.showProgress
import com.develop.base_android.data.network.APIRequest
import com.develop.base_android.data.network.catchRetry
import com.develop.base_android.data.network.getEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRequest: APIRequest
) : BaseViewModel() {

    fun getEntry() {
        viewModelScope.launch {
            showProgress()
            apiRequest.getEntry()
                .catchRetry(this@LoginViewModel){

                }
                .collect {
                    hideProgress()
                    Log.d("KMSHSUPP", it.entries?.get(0)?.Description.toString())
                }
        }
    }
}