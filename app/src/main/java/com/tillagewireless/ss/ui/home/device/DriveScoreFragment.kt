package com.tillagewireless.ss.ui.home.device

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentDriveScoreBinding
import com.tillagewireless.ss.ui.home.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriveScoreFragment: Fragment(R.layout.fragment_drive_score) {
    private lateinit var binding: FragmentDriveScoreBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDriveScoreBinding.bind(view)
    }
}