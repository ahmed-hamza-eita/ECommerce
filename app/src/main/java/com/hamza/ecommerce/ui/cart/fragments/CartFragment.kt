package com.hamza.ecommerce.ui.cart.fragments;


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentCartBinding
import com.hamza.ecommerce.ui.cart.viewmodel.CartViewModel
import com.hamza.ecommerce.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {


    override val viewModel: CartViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_cart


    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "RegisterFragment"

    }
}