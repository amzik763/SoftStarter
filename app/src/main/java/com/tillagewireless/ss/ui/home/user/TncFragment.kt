package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentTncBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TncFragment: Fragment(R.layout.fragment_tnc) {
    private lateinit var binding: FragmentTncBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTncBinding.bind(view)
    }
}