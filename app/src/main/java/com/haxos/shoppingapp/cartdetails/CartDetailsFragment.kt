package com.haxos.shoppingapp.cartdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.haxos.shoppingapp.databinding.FragmentCartdetailsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CartDetailsFragment : DaggerFragment() {

    private val args: CartDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CartDetailsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentCartdetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartdetailsBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner

            groceries.adapter = GroceriesAdapter()
        }

        viewModel.start(args.cartId)

        return binding.root
    }
}