package com.hamza.ecommerce.ui.auth.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.hamza.ecommerce.databinding.FragmentRegisterBinding
import com.hamza.ecommerce.ui.auth.viewmodel.RegisterViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.RegisterViewModelFactory
import com.hamza.ecommerce.utils.BindingFragment

class RegisterFragment : BindingFragment<FragmentRegisterBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegisterBinding::inflate

    private val loginViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = loginViewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}