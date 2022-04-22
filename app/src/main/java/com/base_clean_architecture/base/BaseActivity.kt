package com.base_clean_architecture.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseActivity<V : BaseViewModel, B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B
    var navigation: Navigation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeViewBinding()
        setContentView(binding.root)
        setupView(savedInstanceState)
    }

    open fun makeViewBinding() {}

    open fun setupView(savedInstanceState: Bundle?) {}

    override fun onBackPressed() {
        if (navigation == null || navigation?.fragments?.size == 1) {
            finish()
        } else {
            navigation?.fragments?.removeLast()
            super.onBackPressed()
        }
    }
}