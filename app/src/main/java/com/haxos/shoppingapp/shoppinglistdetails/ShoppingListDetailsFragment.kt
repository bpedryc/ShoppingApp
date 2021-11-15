package com.haxos.shoppingapp.shoppinglistdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.haxos.shoppingapp.R
import com.haxos.shoppingapp.databinding.FragmentShoppinglistdetailsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShoppingListDetailsFragment : DaggerFragment() {

    private val args: ShoppingListDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ShoppingListDetailsViewModel> { viewModelFactory }

    private var _binding: FragmentShoppinglistdetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var addGroceryDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppinglistdetailsBinding
            .inflate(inflater, container, false).apply {
                viewmodel = viewModel
                lifecycleOwner = viewLifecycleOwner

                groceries.adapter = GroceriesAdapter(viewModel)
        }

        setupAddGroceryDialog()
        setupFab()
        setupErrorToast()

        viewModel.start(args.shoppingListId)

        return binding.root
    }

    private fun setupErrorToast() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAddGroceryDialog() {
        addGroceryDialog = AlertDialog.Builder(context)
            .setTitle(R.string.dialogtitle_grocery_add)
            .setMessage(R.string.dialogmessage_grocery_add)
            .setView(R.layout.dialogview_singleinput)
            .setPositiveButton(R.string.dialogbutton_add) { _, _ -> createGrocery() }
            .setNegativeButton(R.string.dialogbutton_cancel) { _, _ -> clearDialogInput() }
            .create()
    }

    private fun setupFab() {
        binding.fabAddGrocery.setOnClickListener {
            addGroceryDialog.show()
        }
    }

    private fun createGrocery() {
        val groceryNameInput = addGroceryDialog.findViewById<EditText>(R.id.dialoginput)
        val groceryName = groceryNameInput.text.toString()
        viewModel.createGrocery(args.shoppingListId, groceryName)
        groceryNameInput.text.clear()
    }

    private fun clearDialogInput() {
        val groceryNameInput = addGroceryDialog.findViewById<EditText>(R.id.dialoginput)
        groceryNameInput.text.clear()
    }
}