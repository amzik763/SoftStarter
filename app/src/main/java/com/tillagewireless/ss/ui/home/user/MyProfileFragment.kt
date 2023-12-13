package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentMyProfileBinding
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyProfileFragment: Fragment(R.layout.fragment_my_profile){
    private lateinit var binding: FragmentMyProfileBinding
    private val viewModel by activityViewModels<UserViewModel>()
    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentMyProfileBinding.bind(view)
        getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        it.value.user?.let { user -> viewModel.saveUser(user) }
                    }
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })
    }

    private fun getUser(){
        binding.progressbar.visible(false)
        viewModel.getUser()
    }

    private fun updateUser(){

    }
}