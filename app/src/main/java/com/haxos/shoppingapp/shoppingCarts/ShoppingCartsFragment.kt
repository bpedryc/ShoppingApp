package com.haxos.shoppingapp.shoppingCarts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.haxos.shoppingapp.databinding.FragmentShoppingcartsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShoppingCartsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ShoppingCartsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentShoppingcartsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingcartsBinding.inflate(inflater, container, false).apply {
            shoppingCarts.adapter = ShoppingCartsAdapter(viewModel)

            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}