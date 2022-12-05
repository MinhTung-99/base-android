package com.develop.base_android.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.develop.base_android.application.base.BaseDialogFragment
import com.develop.base_android.databinding.CustomDialogAlertBinding
import com.develop.base_android.resource.utils.setOnSingleClick

class CustomAlertDialog : BaseDialogFragment<CustomDialogAlertBinding>() {

    companion object {

        const val ALERT_MSG_KEY = "ALERT_MSG_KEY"
        const val ALERT_POS_TITLE_KEY = "ALERT_POS_TITLE_KEY"
        const val ALERT_NEV_TITLE_KEY = "ALERT_NEV_TITLE_KEY"
        const val ALERT_CALLBACK = "ALERT_CALLBACK"


        fun show(fMNG: FragmentManager) {
            val dialog = CustomAlertDialog()
           // dialog.arguments = data.toBundle()
            dialog.show(fMNG, "")
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): CustomDialogAlertBinding {
        return CustomDialogAlertBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
       // val callback = arguments?.serializable(ALERT_CALLBACK) as? ((Boolean) -> Unit)
        //binding.tvMessage.text = arguments?.getString(ALERT_MSG_KEY)
       // binding.tvPos.text = arguments?.getString(ALERT_POS_TITLE_KEY)
        binding.tvPos.setOnSingleClick {
            //if (callback != null) callback(true)
            dismiss()
        }

        if (arguments?.getString(ALERT_NEV_TITLE_KEY) != null) {
            /*binding.tvNev.visible()
            binding.tvNev.text = arguments?.getString(ALERT_NEV_TITLE_KEY)
            binding.tvNev.setOnSingleClick {
                if (callback != null) callback(false)
                dismiss()
            }*/
        }
    }
}