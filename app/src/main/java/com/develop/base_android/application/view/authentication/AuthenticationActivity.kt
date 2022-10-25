package com.develop.base_android.application.view.authentication

import android.os.Bundle
import androidx.activity.viewModels
import com.develop.base_android.application.base.BaseVMActivity
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.databinding.ActivityAuthenticationBinding

class AuthenticationActivity :
    BaseVMActivity<AuthenticationViewModel, ActivityAuthenticationBinding>() {
    override val viewModel: AuthenticationViewModel by viewModels()

    override fun makeViewBinding() {
        super.makeViewBinding()
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
    }
}

class AuthenticationViewModel : BaseViewModel() {

}