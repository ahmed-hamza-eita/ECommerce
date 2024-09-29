package com.hamza.ecommerce.ui.home.fragment;


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentHomeBinding
import com.hamza.ecommerce.ui.home.viewmodel.HomeViewModel
import com.hamza.ecommerce.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_home


    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "RegisterFragment"

    }
}