package com.hamza.ecommerce.ui.account.fragments;


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentAccountBinding
import com.hamza.ecommerce.ui.account.viewmodel.AccountViewModel
import com.hamza.ecommerce.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>() {


    override val viewModel: AccountViewModel by viewModels ()

    override fun getLayoutResId(): Int = R.layout.fragment_account


    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "RegisterFragment"

    }
}