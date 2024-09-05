package com.hamza.ecommerce.ui.auth.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentForgetPasswordBinding
import com.hamza.ecommerce.ui.auth.viewmodel.ForgetPasswordViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.ForgetPasswordViewModelFactory
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModelFactory


class ForgetPasswordFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModels {
        ForgetPasswordViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = forgetPasswordViewModel

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val TAG = "ForgetPasswordFragment"
    }

}