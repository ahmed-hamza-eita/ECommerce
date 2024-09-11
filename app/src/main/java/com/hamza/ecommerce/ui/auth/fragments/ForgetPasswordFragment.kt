package com.hamza.ecommerce.ui.auth.fragments;

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamza.ecommerce.R
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.databinding.FragmentForgetPasswordBinding
import com.hamza.ecommerce.ui.auth.viewmodel.ForgetPasswordViewModel
import com.hamza.ecommerce.utils.BaseBottomSheetFragment
import com.hamza.ecommerce.utils.showDialog
import com.hamza.ecommerce.utils.showSnakeBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment :
    BaseBottomSheetFragment<FragmentForgetPasswordBinding, ForgetPasswordViewModel>() {


    override val viewModel: ForgetPasswordViewModel by viewModels()


    override fun getLayoutResId(): Int = R.layout.fragment_forget_password


    override fun init() {
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.restPasswordState.collect { resource ->
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