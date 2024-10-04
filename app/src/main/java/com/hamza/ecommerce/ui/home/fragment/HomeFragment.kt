package com.hamza.ecommerce.ui.home.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.hamza.ecommerce.R
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.databinding.FragmentHomeBinding
import com.hamza.ecommerce.ui.common.customviews.CircleView
import com.hamza.ecommerce.ui.common.customviews.sliderIndicatorsView
import com.hamza.ecommerce.ui.common.customviews.updateIndicators
import com.hamza.ecommerce.ui.home.adapters.SalesAdAdapter
import com.hamza.ecommerce.ui.home.models.SalesAdUIModel
import com.hamza.ecommerce.ui.home.viewmodel.HomeViewModel
import com.hamza.ecommerce.utils.BaseFragment
import com.hamza.ecommerce.utils.DepthPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                imageUrl = "data:image/jpeg;base64,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"
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
        initViewModel()

    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.salesAdsState.collect { resources ->
                when (resources) {

                    is Resource.Loading -> {
                        Log.d(TAG, "initViewModel: Loading")
                    }

                    is Resource.Success -> {
                        binding.saleAdsShimmerView.root.apply {
                            stopShimmer()
                            View.GONE
                        }
                        initSalesAdsView(resources.data)

                    }

                    is Resource.Error -> {
                        Log.e(TAG, "initViewModel: ${"Error"}")
                    }


                }
            }
        }
    }


    private fun initSalesAdsView(salesAds: List<SalesAdUIModel>?) {
        if (salesAds.isNullOrEmpty()) {
            return
        }

        sliderIndicatorsView(
            requireContext(),
            binding.saleAdsViewPager,
            binding.indicatorView,
            indicators,
            salesAds.size
        )

        val salesAdapter = SalesAdAdapter(lifecycleScope, salesAds)
        binding.saleAdsViewPager.apply {
            adapter = salesAdapter
            setPageTransformer(DepthPageTransformer())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateIndicators(requireContext(), indicators, position)
                }
            })
        }

        lifecycleScope.launch(IO) {
            tickerFlow(5000).collect {
                withContext(Main) {
                    binding.saleAdsViewPager.setCurrentItem(
                        (binding.saleAdsViewPager.currentItem + 1) % salesAds.size, true
                    )
                }
            }
        }

        // add animation from top to bottom
        binding.saleAdsViewPager.animate().translationY(0f).alpha(1f).setDuration(500).start()

    }

    private fun tickerFlow(period: Long) = flow {
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

    private var indicators = mutableListOf<CircleView>()


    companion object {
        private const val TAG = "HomeFragment"

    }
}