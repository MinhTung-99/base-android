package com.develop.base_android.application.base.tabbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.develop.base_android.application.base.BaseFragment
import com.develop.base_android.application.base.mActivity
import com.develop.base_android.databinding.CollectionBinding
import com.develop.base_android.view.login.LoginFragment
import com.develop.base_android.view.sign_up.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabbarFragment : BaseFragment<CollectionBinding>(){
    /*init {
        isVisibleTabbar = true
    }*/

    private val fragments: List<Fragment> =
        listOf(LoginFragment(), SignUpFragment())

    private lateinit var collectionAdapter: CollectionPagerAdapter

    val currentSelectedPosition: Int
        get() = binding.pager.currentItem

    override fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        super.makeViewBinding(inflater, container, savedInstanceState)
        binding = CollectionBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()
        collectionAdapter = CollectionPagerAdapter(this)
        binding.pager.isSaveEnabled = false
        binding.pager.isUserInputEnabled = false
        binding.pager.offscreenPageLimit = fragments.size
        binding.pager.adapter = collectionAdapter
        collectionAdapter.setUpFragment(fragments = fragments)
        binding.pager.setCurrentItem(0, false)

        (mActivity?.tabbar as? TabbarView)?.didSelectItemAt = {
            /*when (it) {
                *//*0 -> setCurrentItem(it)
                1, 2, 3 -> mActivity?.checkShowAuthen {
                    setCurrentItem(it)
                }*//*

            }*/
            setCurrentItem(it)
        }
    }

    fun setCurrentItem(index: Int, scrollEnable: Boolean = false) {
        binding.pager.setCurrentItem(index, scrollEnable)
    }

    fun getFragment(index: Int): Fragment {
        return fragments[index]
    }
}