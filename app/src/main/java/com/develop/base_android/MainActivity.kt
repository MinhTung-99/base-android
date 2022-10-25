package com.develop.base_android

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.develop.base_android.application.base.BaseVMActivity
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.BaseViewModelFactory
import com.develop.base_android.databinding.ActivityMainBinding


class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels {
        BaseViewModelFactory("my awesome param")
    }

    override fun makeViewBinding() {
        super.makeViewBinding()
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        viewModel.test()

        binding.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}

class MainViewModel(val mParam: String) : BaseViewModel() {

    fun test() {
        Log.d("KMSJISS", "RUNNING = $mParam")
    }
}