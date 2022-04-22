package com.base_clean_architecture.base

typealias MyFragment = BaseFragment<*, *>
typealias MyActivity = BaseActivity<*, *>

interface Navigation {
    var fragments: MutableList<MyFragment>
    fun push(fragment: MyFragment, tag: String? = null, withAnimation: Boolean = true)
    fun pop(tag: String? = null)
    fun setRoot(fragment: MyFragment)
}