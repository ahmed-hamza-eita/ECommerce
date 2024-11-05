package com.hamza.ecommerce.ui.auth.fragments;


import androidx.fragment.app.viewModels
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentCountriesBinding
import com.hamza.ecommerce.ui.auth.viewmodel.CountriesViewModel
import com.hamza.ecommerce.utils.BaseFragment

class CountriesFragment : BaseFragment<FragmentCountriesBinding, CountriesViewModel>() {


    override val viewModel: CountriesViewModel by viewModels  ()

    override fun getLayoutResId(): Int = R.layout.fragment_countries


    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "RegisterFragment"

    }
}