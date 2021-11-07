package com.haxos.shoppingapp.shoppinglistdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.haxos.shoppingapp.databinding.FragmentShoppinglistdetailsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShoppingListDetailsFragment : DaggerFragment() {

    private val args: ShoppingListDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ShoppingListDetailsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentShoppinglistdetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppinglistdetailsBinding
            .inflate(inflater, container, false).apply {
                viewmodel = viewModel
                lifecycleOwner = viewLifecycleOwner

                groceries.adapter = GroceriesAdapter()
        }

        viewModel.start(args.shoppingListId)

        return binding.root
    }
}