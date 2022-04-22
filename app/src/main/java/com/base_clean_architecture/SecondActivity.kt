package com.base_clean_architecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.base_clean_architecture.base.BaseActivity
import com.base_clean_architecture.base.impl.NavigationImpl
import com.base_clean_architecture.databinding.ActivitySecondBinding
import com.base_clean_architecture.second.SecondFragment

class SecondActivity : BaseActivity<MainViewModel, ActivitySecondBinding>() {


    override fun makeViewBinding() {
        super.makeViewBinding()

        binding = ActivitySecondBinding.inflate(layoutInflater)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        val secondFragment = SecondFragment()
        navigation = NavigationImpl(this, R.id.rootViewSecond, secondFragment)
    }
}