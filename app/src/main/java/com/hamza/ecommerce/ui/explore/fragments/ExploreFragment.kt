package com.hamza.ecommerce.ui.explore.fragments;


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentExploreBinding
import com.hamza.ecommerce.ui.explore.viewmodel.ExploreViewModel
import com.hamza.ecommerce.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreViewModel>() {


    override val viewModel: ExploreViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_explore


    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "RegisterFragment"

    }
}