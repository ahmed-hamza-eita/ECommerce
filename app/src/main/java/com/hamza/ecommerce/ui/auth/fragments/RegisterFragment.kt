package com.hamza.ecommerce.ui.auth.fragments;

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hamza.ecommerce.R
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.databinding.FragmentRegisterBinding
import com.hamza.ecommerce.ui.auth.viewmodel.RegisterViewModel
import com.hamza.ecommerce.utils.BaseFragment
import com.hamza.ecommerce.utils.showSnakeBarError
import com.hamza.ecommerce.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding,RegisterViewModel>() {


    override val viewModel: RegisterViewModel by viewModels()

    override fun getLayoutResId(): Int  = R.layout.fragment_register





    override fun init() {
        initListeners()
        initViewModel()
    }


    private fun initListeners() {
        binding.apply {
            btnGoToSignInScreen.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.registerState.collect { registerState ->
                Log.d(TAG, "initViewModel $registerState")
                registerState.let { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }

                        is Resource.Success -> {
                            progressDialog.dismiss()
                            //  requireContext().showToast(resource.data.toString())
                            requireContext().showToast("check your email to verify your account")
                            findNavController().popBackStack()

                        }

                        is Resource.Error -> {
                            progressDialog.dismiss()
                            view?.showSnakeBarError(
                                resource.exception?.message ?: getString(R.string.generic_err_msg)
                            )
                        }
                    }

                }

            }
        }
    }

    companion object {
        private const val TAG = "RegisterFragment"

    }
}