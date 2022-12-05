package com.develop.base_android.view.test_base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.develop.base_android.R
import com.develop.base_android.application.base.BaseVMActivity
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.databinding.ActivityTestBaseBinding
import com.develop.base_android.view.dialog.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TestBaseActivity : BaseVMActivity<TestBaseViewModel, ActivityTestBaseBinding>() {

    override val viewModel: TestBaseViewModel by viewModels()

    private val adapter: ButtonTestBaseAdapter by lazy {
        ButtonTestBaseAdapter {
            when(it) {
                0 -> {
                    CustomAlertDialog.show(supportFragmentManager)
                }
            }
        }
    }

    override fun makeViewBinding() {
        super.makeViewBinding()
        binding = ActivityTestBaseBinding.inflate(layoutInflater)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        binding.rvButton.adapter = adapter

        viewModel.getButtons()
        viewModel.buttonsLiveDate.observe(this) {
            adapter.setUpData(it)
        }
    }
}

@HiltViewModel
class TestBaseViewModel @Inject constructor() : BaseViewModel() {

    val buttonsLiveDate = MutableLiveData<MutableList<ButtonTestBaseModel>>()

    fun getButtons () {
        val buttons = mutableListOf<ButtonTestBaseModel>()
        buttons.add(ButtonTestBaseModel("Dialog"))

        buttonsLiveDate.postValue(buttons)
    }

}