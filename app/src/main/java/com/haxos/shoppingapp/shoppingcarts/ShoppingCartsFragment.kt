package com.haxos.shoppingapp.shoppingcarts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.haxos.shoppingapp.EventObserver
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

        viewModel.openCartEvent.observe(viewLifecycleOwner, EventObserver {
            openCartDetails(it)
        })

        return binding.root
    }

    private fun openCartDetails(shoppingCartId: String) {
        val action = ShoppingCartsFragmentDirections
            .actionShoppingCartsFragmentToCartDetailsFragment(shoppingCartId)
        findNavController().navigate(action)
    }
}