package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tillagewireless.R
import com.tillagewireless.databinding.DialogLogoutBinding
import com.tillagewireless.ss.ui.logout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : DialogFragment(R.layout.dialog_logout) {

    private lateinit var binding: DialogLogoutBinding
    private val viewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_logout, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MENU_ITEM", "Dialog on view created")
        binding = DialogLogoutBinding.bind(view)

        dialog?.show()

        binding.btCancel.setOnClickListener{
            dialog?.dismiss()
        }

        binding.btLogout.setOnClickListener{
            logout()
            dialog?.dismiss()
        }
    }
}