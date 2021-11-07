package com.haxos.shoppingapp.shoppinglists

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.haxos.shoppingapp.R
import com.haxos.shoppingapp.databinding.FragmentShoppinglistsBinding
import com.haxos.shoppingapp.utils.EventObserver
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

        setupNavigation()
        setupFab()
        setupRefresh()

        viewModel.loadShoppingLists()

        return binding.root
    }

    private fun setupRefresh() {
        viewModel.createdShoppingListEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.loadShoppingLists()
        })
    }

    private fun openShoppingListDetails(shoppingListId: String) {
        val action = ShoppingListsFragmentDirections
            .actionShoppingListsFragmentToShoppingListDetailsFragment(shoppingListId)
        findNavController().navigate(action)
    }

    private fun setupNavigation() {
        viewModel.openShoppingListEvent.observe(viewLifecycleOwner, EventObserver {
            openShoppingListDetails(it)
        })
    }

    private fun setupFab() {
        binding.fabAddShoppinglist.setOnClickListener {
            displayNewShoppingListDialog()
        }
    }

    private fun displayNewShoppingListDialog() {
        val nameEditText = EditText(context)

        AlertDialog.Builder(context)
            .setTitle(R.string.dialogtitle_shoppinglist_add)
            .setView(nameEditText)
            .setPositiveButton(R.string.dialogbutton_add) { _, _ ->
                viewModel.createShoppingList(nameEditText.text.toString())
            }
            .setNegativeButton(R.string.dialogbutton_cancel) { _, _ -> }
            .show()
    }
}