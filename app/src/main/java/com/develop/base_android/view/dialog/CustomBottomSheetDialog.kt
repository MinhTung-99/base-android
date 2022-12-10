package com.develop.base_android.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.develop.base_android.R
import com.develop.base_android.application.base.BaseBottomSheetDialogFragment
import com.develop.base_android.databinding.CustomBottomSheetDialogBinding

class CustomBottomSheetDialog(
    private val titles: List<ItemBottomSheet>,
    private val clickItem: (model: ItemBottomSheet) -> Unit
) : BaseBottomSheetDialogFragment<CustomBottomSheetDialogBinding>() {

    private val adapter: BottomSheetAdapter by lazy {
        BottomSheetAdapter(titles, clickItem)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): CustomBottomSheetDialogBinding {
        binding = CustomBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding;
    }

    override fun setupView() {
        super.setupView()

        binding.rvBottomSheet.adapter = adapter
    }

    override fun getTheme(): Int {
        return R.style.CustomSelectRoleBottomSheetDialog
    }
}

class ItemBottomSheet (val title: String)