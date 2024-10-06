package com.hamza.ecommerce.ui.home.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
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
                            visibility = View.GONE
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