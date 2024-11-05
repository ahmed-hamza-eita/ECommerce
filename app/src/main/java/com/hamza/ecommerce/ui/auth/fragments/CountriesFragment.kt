package com.hamza.ecommerce.ui.auth.fragments;


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentCountriesBinding
import com.hamza.ecommerce.ui.auth.adapters.CountriesAdapter
import com.hamza.ecommerce.ui.auth.adapters.CountryClickListener
import com.hamza.ecommerce.ui.auth.models.CountryUIModel
import com.hamza.ecommerce.ui.auth.viewmodel.CountriesViewModel
import com.hamza.ecommerce.utils.BaseBottomSheetFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CountriesFragment : BaseBottomSheetFragment<FragmentCountriesBinding, CountriesViewModel>(),
    CountryClickListener {


    override val viewModel: CountriesViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_countries


    override fun init() {
         initViewModel()
    }

    private fun initViewModel() {

        lifecycleScope.launch {
            viewModel.countriesUIModelState.collectLatest {
                if (it.isEmpty()) return@collectLatest
                binding.progressBar.visibility = View.GONE
                binding.countriesLayout.visibility = View.VISIBLE

                val countriesAdapter = CountriesAdapter(it, this@CountriesFragment)
                binding.countriesRv.apply {
                    adapter = countriesAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onCountryClicked(country: CountryUIModel) {
        viewModel.saveUserCountry(country)
        dismiss()
    }

    companion object {
        private const val TAG = "RegisterFragment"

    }
}