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
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepositoryImpl
import com.hamza.ecommerce.databinding.FragmentLoginBinding
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModel
import com.hamza.ecommerce.utils.BindingFragment
import com.hamza.ecommerce.utils.CrashlyticsUtils
import com.hamza.ecommerce.utils.CrashlyticsUtils.CUSTOM_KEY
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment : BindingFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by lazy {
        LoginViewModel(
            userPrefs = UserPreferencesRepositoryImpl(
                UserPreferencesDataStore(
                    requireContext()
                )
            ), authRepository = FirebaseAuthRepositoryImpl()
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

    }

    companion object {
        const val TAG = "LoginFragment"
    }
}
