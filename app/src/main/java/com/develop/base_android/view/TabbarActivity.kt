package com.develop.base_android.view

import android.os.Bundle
import androidx.activity.viewModels
import com.develop.base_android.R
import com.develop.base_android.application.base.BaseVMActivity
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.tabbar.TabbarView
import com.develop.base_android.databinding.ActivityTabbarBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TabbarActivity : BaseVMActivity<TabbarViewModel, ActivityTabbarBinding>() {
    override val viewModel: TabbarViewModel by viewModels()


    override fun makeViewBinding() {
        super.makeViewBinding()
        binding = ActivityTabbarBinding.inflate(layoutInflater)
        tabbar = binding.tabbar
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        binding.tabbar.setDot(1, true)
    }

    override var rootId: Int? = R.id.tabbarFragment

}

@HiltViewModel
class TabbarViewModel @Inject constructor() : BaseViewModel() {

}