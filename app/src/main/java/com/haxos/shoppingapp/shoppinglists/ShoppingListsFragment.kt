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
import com.google.android.material.tabs.TabLayout
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

    private lateinit var addShoppingListDialog: AlertDialog

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

        setupTab()
        setupNavigation()
        setupFab()
        setupRefresh()
        setupDialogs()

        viewModel.loadShoppingLists()

        return binding.root
    }

    private fun setupTab() {
        binding.tabsShoppinglists.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setFilterBasedOnTabPosition(tab.position)
                viewModel.loadShoppingLists()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        setFilterBasedOnTabPosition(binding.tabsShoppinglists.selectedTabPosition)
    }

    private fun setupNavigation() {
        viewModel.openShoppingListEvent.observe(viewLifecycleOwner, EventObserver {
            openShoppingListDetails(it)
        })
    }

    private fun setupFab() {
        binding.fabAddShoppinglist.setOnClickListener {
            addShoppingListDialog.show()
        }
    }

    private fun setupRefresh() {
        viewModel.createdShoppingListEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.loadShoppingLists()
        })
        viewModel.archivedShoppingListEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.loadShoppingLists()
        })
    }

    private fun setupDialogs() {
        addShoppingListDialog = AlertDialog.Builder(context)
            .setTitle(R.string.dialogtitle_shoppinglist_add)
            .setMessage(R.string.dialogmessage_shoppinglist_add)
            .setView(R.layout.dialogview_singleinput)
            .setPositiveButton(R.string.dialogbutton_add) { _, _ -> createShoppingList() }
            .setNegativeButton(R.string.dialogbutton_cancel) { _, _ -> clearDialogInput() }
            .create()
    }

    private fun setFilterBasedOnTabPosition(tabPosition: Int) {
        if (tabPosition == 0) {
            viewModel.setFilter(ShoppingListFilter.ACTIVE)
        } else if (tabPosition == 1) {
            viewModel.setFilter(ShoppingListFilter.ARCHIVED)
        }
    }

    private fun openShoppingListDetails(shoppingListId: String) {
        val action = ShoppingListsFragmentDirections
            .actionShoppingListsFragmentToShoppingListDetailsFragment(shoppingListId)
        findNavController().navigate(action)
    }

    private fun createShoppingList() {
        val shoppingListNameInput = addShoppingListDialog.findViewById<EditText>(R.id.dialoginput)
        val shoppingListName = shoppingListNameInput.text.toString()
        viewModel.createShoppingList(shoppingListName)
        shoppingListNameInput.text.clear()
    }

    private fun clearDialogInput() {
        val shoppingListNameInput = addShoppingListDialog.findViewById<EditText>(R.id.dialoginput)
        shoppingListNameInput.text.clear()
    }
}