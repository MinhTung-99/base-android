package com.develop.base_android.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import com.develop.base_android.R
import com.develop.base_android.application.base.BaseVMActivity
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.pushTo
import com.develop.base_android.data.local.ItemMenu
import com.develop.base_android.databinding.ActivityDarwerBinding
import com.develop.base_android.resource.customView.setOnLeftButtonClick
import com.develop.base_android.resource.customView.setOnRightButtonClick
import com.develop.base_android.view.menu.ItemMenuAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DarwerActivity : BaseVMActivity<DarwerViewModel, ActivityDarwerBinding>() {
    override val viewModel: DarwerViewModel by viewModels()

    private lateinit var adapter: ItemMenuAdapter

    override fun makeViewBinding() {
        super.makeViewBinding()
        binding = ActivityDarwerBinding.inflate(layoutInflater)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        adapter = ItemMenuAdapter(this) {
            setDrawerVisible(false)
            onMenuItemClick(it)
        }

        binding.header.setOnLeftButtonClick { setDrawerVisible(true) }
        binding.header.setOnRightButtonClick {
            Toast.makeText(this, "HEADER RIGHT CLICKED", Toast.LENGTH_SHORT).show()
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })

        binding.rcvListItem.adapter = adapter

        viewModel.getItem()

        viewModel.listMenu.observe(this) {
            adapter.setupData(it)
        }
    }

    private fun onMenuItemClick(menu: ItemMenu) {
        when (menu) {
            ItemMenu.SETTING -> {
                Toast.makeText(this, "SETTING", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "ELSE", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setDrawerVisible(isVisible: Boolean) {
        if (isVisible) {
            binding.drawerLayout.openDrawer(binding.drawer)
        } else {
            binding.drawerLayout.closeDrawer(binding.drawer)
        }
    }
}

@HiltViewModel
class DarwerViewModel @Inject constructor() : BaseViewModel() {
    val listMenu = MutableLiveData<List<ItemMenu>>()

    fun getItem () {
        val listItemMenu = mutableSetOf<ItemMenu>()
        listItemMenu.add(ItemMenu.SETTING)
        listItemMenu.add(ItemMenu.NOTIFICATION)
        listMenu.postValue(listItemMenu.toList())
    }
}