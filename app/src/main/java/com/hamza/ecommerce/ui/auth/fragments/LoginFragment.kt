package com.hamza.ecommerce.ui.auth.fragments;

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.hamza.ecommerce.data.datasource.datastore.UserPreferencesDataStore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepositoryImpl
import com.hamza.ecommerce.databinding.FragmentLoginBinding
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModelFactory
import com.hamza.ecommerce.ui.common.customviews.ProgressDialog
import com.hamza.ecommerce.utils.BindingFragment
import com.hamza.ecommerce.utils.CrashlyticsUtils
import com.hamza.ecommerce.utils.CrashlyticsUtils.CUSTOM_KEY
import com.hamza.ecommerce.utils.showToast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment : BindingFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate


    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            userPrefs = UserPreferencesRepositoryImpl(UserPreferencesDataStore(requireActivity())),
            authRepository = FirebaseAuthRepositoryImpl()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = loginViewModel
        }
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            loginViewModel.loginState.collect { loginState ->
                Log.d(TAG, "initViewModel $loginState")
                loginState.let { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }

                        is Resource.Success -> {
                            progressDialog.dismiss()
                            requireContext().showToast(resource.data.toString())
                        }

                        is Resource.Error -> {
                            progressDialog.dismiss()
                            requireContext().showToast(resource.exception?.message.toString())
                        }
                    }

                }

            }
        }
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}
