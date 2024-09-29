package com.hamza.ecommerce.ui.offers.fragments;


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentOffersBinding
import com.hamza.ecommerce.ui.offers.viewmodel.OffersViewModel
import com.hamza.ecommerce.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class OffersFragment : BaseFragment<FragmentOffersBinding, OffersViewModel>() {


    override val viewModel: OffersViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_offers


    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "RegisterFragment"

    }
}