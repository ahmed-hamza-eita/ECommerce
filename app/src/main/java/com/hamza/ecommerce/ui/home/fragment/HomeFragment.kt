package com.hamza.ecommerce.ui.home.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.FragmentHomeBinding
import com.hamza.ecommerce.ui.home.adapters.SalesAdAdapter
import com.hamza.ecommerce.ui.home.models.SalesAdUIModel
import com.hamza.ecommerce.ui.home.viewmodel.HomeViewModel
import com.hamza.ecommerce.utils.BaseFragment
import com.hamza.ecommerce.utils.DepthPageTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val salesAds = listOf(
            SalesAdUIModel(
                title = "Title",
                description = "Description",
                imageUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"
            ),
            SalesAdUIModel(
                title = "Title2",
                description = "Description2",
                imageUrl = "https://www.google.com/images/branding/googlelogo/3x/googlelogo_color_92x30dp.png"
            ),
        )
        val adapter = SalesAdAdapter(lifecycleScope, salesAds)
        binding.apply {
            saleAdsViewPager.adapter = adapter
            saleAdsViewPager.setPageTransformer(DepthPageTransformer())

        }

    }

    override fun init() {
        //  initListeners()
        //  initViewModel()
    }


    companion object {
        private const val TAG = "HomeFragment"

    }
}