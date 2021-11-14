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

private const val BUNDLE_SELECTED_TAB = "BUNDLE_SELECTED_TAB"

class ShoppingListsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ShoppingListsViewModel> { viewModelFactory }

    private var _binding: FragmentShoppinglistsBinding? = null
    private val binding get() = _binding!!

    var selectedTab = 0

    private lateinit var addShoppingListDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppinglistsBinding.inflate(inflater, container, false).apply {
            shoppingLists.adapter = ShoppingListsAdapter(viewModel)

            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setupTab(savedInstanceState)
        setupNavigation()
        setupFab()
        setupDialogs()

        viewModel.loadShoppingLists()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BUNDLE_SELECTED_TAB, selectedTab)
    }

    private fun setupTab(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            val savedTab = savedInstanceState.getInt(BUNDLE_SELECTED_TAB, 0)
            selectedTab = savedTab
        }

        binding.tabsShoppinglists.apply {

            getTabAt(selectedTab)?.select()
            setFilterBasedOnTabPosition(selectedTab)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    selectedTab = tab.position
                    setFilterBasedOnTabPosition(selectedTab)
                    viewModel.loadShoppingLists()
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
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