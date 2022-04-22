package com.base_clean_architecture.coffee

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.base_clean_architecture.SecondActivity
import com.base_clean_architecture.base.BaseFragment
import com.base_clean_architecture.data.network.ApiClient
import com.base_clean_architecture.databinding.FragmentLoginBinding
import com.base_clean_architecture.domain.impl.RegistrationRepositoryImpl
import com.base_clean_architecture.domain.usercase.LoginUserCase
import com.base_clean_architecture.second.SecondFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoffeeFragment : BaseFragment<CoffeeViewModel, FragmentLoginBinding>() {

    private lateinit var adapter: CoffeeAdapter

    override fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()

        binding.btnNextActivity.setOnClickListener {
            startActivity(Intent(requireActivity(), SecondActivity::class.java))
        }
        binding.btnNextFragment.setOnClickListener {
            navigation?.push(SecondFragment(), "ABCC")
        }

        adapter = CoffeeAdapter()
        binding.rvCoffee.adapter = adapter

        showProgress(true)
        viewModel.getCoffee()
        viewModel.coffees.observe(this) { coffees ->
            showProgress(false)
            adapter.setupData(coffees)
        }
    }

    override fun makeViewModel() {
        super.makeViewModel()
        viewModel = CoffeeViewModelFactory().create(CoffeeViewModel::class.java)
    }
}