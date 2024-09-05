package com.hamza.ecommerce.ui.auth.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamza.ecommerce.R
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.databinding.FragmentForgetPasswordBinding
import com.hamza.ecommerce.ui.auth.viewmodel.ForgetPasswordViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.ForgetPasswordViewModelFactory
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModelFactory
import com.hamza.ecommerce.ui.common.customviews.ProgressDialog
import com.hamza.ecommerce.utils.showDialog
import com.hamza.ecommerce.utils.showSnakeBarError
import kotlinx.coroutines.launch


class ForgetPasswordFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModels {
        ForgetPasswordViewModelFactory(requireContext())
    }
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(requireActivity()) }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            forgetPasswordViewModel.restPasswordState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        progressDialog.show()
                                      }

                    is Resource.Success -> {
                        progressDialog.dismiss()
                        showDialog(
                            title = "Reset Password",
                            message = "We have sent you an email to reset your password. Please check your email."
                        )

                    }
                    is Resource.Error -> {
                        progressDialog.dismiss()
                        val msg = resource.exception?.message ?: getString(R.string.generic_err_msg)
                        view?.showSnakeBarError(msg)


                    }
                }
            }
        }
    }

    private fun showSentEmailSuccessDialog() {
        MaterialAlertDialogBuilder(requireActivity()).setTitle("Reset Password")
            .setMessage("We have sent you an email to reset your password. Please check your email.")
            .setPositiveButton(
                "OK"
            ) { dialog, _ ->
                dialog?.dismiss()
                this@ForgetPasswordFragment.dismiss()
            }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val TAG = "ForgetPasswordFragment"
    }

}