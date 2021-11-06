package com.haxos.shoppingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haxos.shoppingapp.databinding.FragmentCartdetailsBinding
import dagger.android.support.DaggerFragment

class CartDetailsFragment : DaggerFragment() {

    private lateinit var binding: FragmentCartdetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartdetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}