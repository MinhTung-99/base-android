package com.base_clean_architecture.base.impl

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.base_clean_architecture.R
import com.base_clean_architecture.base.MyFragment
import com.base_clean_architecture.base.Navigation

class NavigationImpl(
    private val activity: AppCompatActivity,
    @IdRes var containerID: Int,
    root: MyFragment
) : Navigation {
    override var fragments: MutableList<MyFragment> = mutableListOf()

    private val ROOT_TAG = "${this::class.java.simpleName}_Root"

    private var fragmentManager: FragmentManager = activity.supportFragmentManager

    init {
        fragments = mutableListOf()
        setRoot(root)
    }

    override fun push(fragment: MyFragment, tag: String?, withAnimation: Boolean) {
        tag?.let { fragment.myTag = it }
        fragments.add(fragment)
        val transaction = fragmentManager.beginTransaction()
        if (withAnimation) {
            transaction
                .setCustomAnimations(
                    R.anim.from_right, R.anim.to_left,
                    R.anim.from_left, R.anim.to_right
                )
        }
        transaction
            .replace(containerID, fragment)
            .addToBackStack(tag)
            .commit()
    }

    override fun pop(tag: String?) {
        if (tag != null) {
            val index = fragments.indexOfFirst { it.myTag == tag }
            if (index != -1) {
                fragments.removeAll(fragments.subList(index, fragments.size))
                fragmentManager.popBackStack(tag, 0)
            } else {
                activity.onBackPressed()
            }

            return
        }
        activity.onBackPressed()
    }

    override fun setRoot(fragment: MyFragment) {
        fragments.clear()
        fragmentManager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        push(fragment, ROOT_TAG, false)
    }

    override fun popToRoot() {
        fragments.removeAll(fragments.subList(1, fragments.size))
        fragmentManager.popBackStack(ROOT_TAG, 0)
    }
}