package com.develop.base_android.view.test_base

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.develop.base_android.application.base.BaseVMActivity
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.showToast
import com.develop.base_android.databinding.ActivityTestBaseBinding
import com.develop.base_android.view.dialog.*
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
                1 -> {
                    val bottomSheet = CustomBottomSheetDialog(
                        viewModel.getBottomSheet()
                    ) {
                        Log.d("KMHSSS", it.title)
                    }
                    bottomSheet.show(supportFragmentManager, "")
                }
                2 -> {
                    val bottomSheet = WheelPickerBottomSheet(
                        viewModel.getWheelPicker(),
                        0
                    ) { pos, value ->
                        value?.let { it -> Log.d("KMHUSGS", it) }
                    }
                    bottomSheet.show(supportFragmentManager, "")
                }
                3 -> {
                    showToast("NOTIFICATION")
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
        buttons.add(ButtonTestBaseModel("BottomSheet"))
        buttons.add(ButtonTestBaseModel("WheelPicker"))
        buttons.add(ButtonTestBaseModel("ToastNotification"))

        buttonsLiveDate.postValue(buttons)
    }

    fun getBottomSheet (): List<ItemBottomSheet> {
        val bottomSheet = mutableListOf<ItemBottomSheet>()
        bottomSheet.add(ItemBottomSheet("SELECT 1"))
        bottomSheet.add(ItemBottomSheet("SELECT 2"))
        bottomSheet.add(ItemBottomSheet("SELECT 3"))

        return bottomSheet
    }

    fun getWheelPicker (): List<String> {
        val wheelPicker = mutableListOf<String>()
        wheelPicker.add("Item 1")
        wheelPicker.add("Item 2")
        wheelPicker.add("Item 3")

        return wheelPicker
    }

}