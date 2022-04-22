package com.base_clean_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.base_clean_architecture.custom_view.ProgressView

open class BaseFragment<V : BaseViewModel, B : ViewBinding> : Fragment() {

    var myTag: String = this::class.java.simpleName

    lateinit var viewModel: V
    lateinit var binding: B

    private var progress: ProgressView? = null

    var navigation: Navigation? = null

   // private var view: ViewBinding? = null

    private val mActivity: MyActivity? by lazy {
        return@lazy this.activity as? BaseActivity<*, *>
    }

    open fun setupView() {}

    open fun makeViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {}

    open fun makeViewModel(){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //if (view == null) {
            makeViewBinding(inflater, container, savedInstanceState)
            setupView()
        //}
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (progress == null) {
            progress = ProgressView()
        }

        navigation = mActivity?.navigation
        makeViewModel()

    }

    fun showProgress(isShow: Boolean) {
        if (isShow) {
            progress?.show(childFragmentManager, "")
        } else {
            progress?.dismiss()
        }
    }
}