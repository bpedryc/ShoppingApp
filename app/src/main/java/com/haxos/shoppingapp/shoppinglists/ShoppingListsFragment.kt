package com.haxos.shoppingapp.shoppinglists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.haxos.shoppingapp.EventObserver
import com.haxos.shoppingapp.databinding.FragmentShoppinglistsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShoppingListsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ShoppingListsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentShoppinglistsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppinglistsBinding.inflate(inflater, container, false).apply {
            shoppingLists.adapter = ShoppingListsAdapter(viewModel)

            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.openShoppingListEvent.observe(viewLifecycleOwner, EventObserver {
            openShoppingListDetails(it)
        })

        return binding.root
    }

    private fun openShoppingListDetails(shoppingListId: String) {
        val action = ShoppingListsFragmentDirections
            .actionShoppingListsFragmentToShoppingListDetailsFragment(shoppingListId)
        findNavController().navigate(action)
    }
}