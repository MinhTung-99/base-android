package com.base_clean_architecture.coffee

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.base_clean_architecture.SecondActivity
import com.base_clean_architecture.base.BaseFragment
import com.base_clean_architecture.base.adapter.setupLoadMore
import com.base_clean_architecture.databinding.FragmentLoginBinding
import com.base_clean_architecture.second.SecondFragment
import kotlinx.coroutines.*

class CoffeeFragment : BaseFragment<CoffeeViewModel, FragmentLoginBinding>() {

    private lateinit var adapter: CoffeeAdapter
    private var isLoading = false

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
        binding.rvCoffee.setupLoadMore {
            if (!isLoading) {
                isLoading = true
                showProgress(true)
                CoroutineScope(Dispatchers.IO).launch {
                    delay(4000L)
                    isLoading = false
                    withContext(Dispatchers.Main) {
                        showProgress(false)
                        Toast.makeText(requireContext(), "Load more", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun makeViewModel() {
        super.makeViewModel()
        viewModel = CoffeeViewModelFactory().create(CoffeeViewModel::class.java)
    }
}