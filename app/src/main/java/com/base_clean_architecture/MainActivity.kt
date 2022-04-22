package com.base_clean_architecture

import android.os.Bundle
import com.base_clean_architecture.base.BaseActivity
import com.base_clean_architecture.base.impl.NavigationImpl
import com.base_clean_architecture.databinding.ActivityMainBinding
import com.base_clean_architecture.coffee.CoffeeFragment

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun makeViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        val loginFragment = CoffeeFragment()
        navigation = NavigationImpl(this, R.id.rootView, loginFragment)
    }
}