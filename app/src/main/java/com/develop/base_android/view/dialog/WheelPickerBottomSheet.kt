package com.develop.base_android.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.develop.base_android.application.base.BaseBottomSheetDialogFragment
import com.develop.base_android.databinding.WheelPickerBinding

class WheelPickerBottomSheet(
    private val datas: List<String>,
    private val selectedPosition: Int = 0,
    private val didSelect: (pos: Int, value: String?) -> Unit
) : BaseBottomSheetDialogFragment<WheelPickerBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): WheelPickerBinding {
        return WheelPickerBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()

        binding.picker.data = datas
        binding.picker.setSelectedItemPosition(selectedPosition, false)
        binding.picker.setOnItemSelectedListener { _, _, _ ->
            //didSelect(position, data as? String)
        }

        binding.textView50.setOnClickListener {
            didSelect(binding.picker.currentItemPosition, datas[binding.picker.currentItemPosition])
            dismiss()
        }
    }
}