package com.haxos.shoppingapp.shoppinglistdetails;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.haxos.shoppingapp.data.models.Grocery;
import com.haxos.shoppingapp.shoppinglistdetails.GroceriesAdapter.ViewHolder;

import com.haxos.shoppingapp.databinding.ItemGroceryBinding;

public class GroceriesAdapter extends ListAdapter<Grocery, ViewHolder> {

    private final ShoppingListDetailsViewModel viewModel;

    public GroceriesAdapter(ShoppingListDetailsViewModel viewModel) {
        super(groceryDiffCallback());
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery item = getItem(position);
        holder.bind(viewModel, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemGroceryBinding binding;

        private ViewHolder(ItemGroceryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static ViewHolder from(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemGroceryBinding binding = ItemGroceryBinding
                    .inflate(inflater, parent, false);

            return new ViewHolder(binding);
        }

        public void bind(ShoppingListDetailsViewModel viewModel, Grocery item) {
            binding.setGrocery(item);
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }

    @NonNull
    private static DiffUtil.ItemCallback<Grocery> groceryDiffCallback() {
        return new DiffUtil.ItemCallback<Grocery>() {
            @Override
            public boolean areItemsTheSame(@NonNull Grocery oldItem, @NonNull Grocery newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Grocery oldItem, @NonNull Grocery newItem) {
                return oldItem.equals(newItem);
            }
        };
    }
}