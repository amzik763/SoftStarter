package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel by activityViewModels<UserViewModel>()

    private var navController : NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentResetPasswordBinding.bind(view)
        //  binding.progressbar.visible(false)
        //  binding.buttonLogin.enable(false)

    }
}