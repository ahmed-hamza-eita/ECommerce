package com.hamza.ecommerce.ui.auth.fragments;

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.hamza.ecommerce.R
import com.hamza.ecommerce.data.datasource.datastore.UserPreferencesDataStore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepositoryImpl
import com.hamza.ecommerce.databinding.FragmentLoginBinding
import com.hamza.ecommerce.ui.auth.getGoogleRequestIntent
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModel
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModelFactory
import com.hamza.ecommerce.ui.common.customviews.ProgressDialog
import com.hamza.ecommerce.ui.home.MainActivity
import com.hamza.ecommerce.utils.BindingFragment
import com.hamza.ecommerce.utils.CrashlyticsUtils
import com.hamza.ecommerce.utils.CrashlyticsUtils.CUSTOM_KEY
import com.hamza.ecommerce.utils.LoginException
import com.hamza.ecommerce.utils.showSnakeBarError
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
        initListeners()
        initViewModel()
    }

    private fun initListeners() {
        binding.apply {
            btnSignInWithGoogle.setOnClickListener {
                loginWithGoogleRequest()
            }
        }
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

    private fun goToHome() {
        requireActivity().startActivity(Intent(activity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        requireActivity().finish()
    }

    // ActivityResultLauncher for the sign-in intent
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            } else {
                view?.showSnakeBarError(getString(R.string.google_sign_in_field_msg))
            }
        }

    private fun loginWithGoogleRequest() {
        val signInIntent = getGoogleRequestIntent(requireActivity())
        launcher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: Exception) {
            view?.showSnakeBarError(e.message ?: getString(R.string.generic_err_msg))
            val msg = e.message ?: getString(R.string.generic_err_msg)
            logAuthIssueToCrashlytics(msg, "Google")
        }
    }

    private fun logAuthIssueToCrashlytics(msg: String, provider: String) {
        CrashlyticsUtils.sendCustomLogToCrashlytics<LoginException>(
            msg,
            CrashlyticsUtils.LOGIN_KEY to msg,
            CrashlyticsUtils.LOGIN_PROVIDER to provider,
        )
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        loginViewModel.loginWithGoogle(idToken)
    }


    companion object {
        const val TAG = "LoginFragment"
    }
}
