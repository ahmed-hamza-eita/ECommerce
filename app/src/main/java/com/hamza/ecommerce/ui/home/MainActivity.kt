package com.hamza.ecommerce.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.hamza.ecommerce.R
import com.hamza.ecommerce.databinding.ActivityMainBinding
import com.hamza.ecommerce.ui.account.fragments.AccountFragment
import com.hamza.ecommerce.ui.auth.AuthActivity
import com.hamza.ecommerce.ui.cart.fragments.CartFragment
import com.hamza.ecommerce.ui.common.viewmodel.UserViewModel
import com.hamza.ecommerce.ui.explore.fragments.ExploreFragment
import com.hamza.ecommerce.ui.home.adapters.HomeViewPagerAdapter
import com.hamza.ecommerce.ui.home.fragment.HomeFragment
import com.hamza.ecommerce.ui.offers.fragments.OffersFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen
    private val userViewModel: UserViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ifUserLoggedIn()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initViews()


    }

    private fun initViews() {
        initViewPager()
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> binding.homeViewPager.currentItem = 0
                R.id.exploreFragment -> binding.homeViewPager.currentItem = 1
                R.id.cartFragment -> binding.homeViewPager.currentItem = 2
                R.id.offerFragment -> binding.homeViewPager.currentItem = 3
                R.id.accountFragment -> binding.homeViewPager.currentItem = 4
            }
            true
        }
    }

    private fun initViewPager() {
        val fragments = listOf(
            HomeFragment(),
            ExploreFragment(),
            CartFragment(),
            OffersFragment(),
            AccountFragment()
        )

        binding.homeViewPager.offscreenPageLimit = fragments.size
        binding.homeViewPager.adapter = HomeViewPagerAdapter(this, fragments)
        binding.homeViewPager.registerOnPageChangeCallback(
            object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavigationView.menu.getItem(position).isChecked = true
                }
            }
        )
    }

    private fun checkUserLoggedIn() {
        lifecycleScope.launch(Main) {
            val isLoggedIn = userViewModel.isUserLoggedIn().first()
            Log.d(TAG, "OnCreate: isLoggedIn: $isLoggedIn")
            if (isLoggedIn) {
                setContentView(R.layout.activity_main)
            } else {
                //  userViewModel.saveUserLoggedIn(true)
                gotoAuthActivity()
            }
        }
    }

    private fun ifUserLoggedIn() {
        val isLoggedIn = runBlocking { userViewModel.isUserLoggedIn().first() }
        if (!isLoggedIn) {
            gotoAuthActivity()
            return
        }
    }

    private fun gotoAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val option = ActivityOptions.makeCustomAnimation(
            this,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        startActivity(intent, option.toBundle())
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            val userDetails = runBlocking { userViewModel.getUserDetails().first() }
            Log.d(TAG, "initViewModel: user details ${userDetails.email}")

            userViewModel.userDetailsState.collect {
                Log.d(TAG, "initViewModel: user details updated ${it?.email}")
            }

        }
    }

    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen = installSplashScreen()
        } else {
            setTheme(R.style.Theme_Ecommerce)
        }
    }


    /**
     * Keep splash screen on-screen indefinitely. This is useful if you're using a custom Activity
     * for routing.
     */
    @RequiresApi(Build.VERSION_CODES.S)
    private fun keepSplashScreenIndefinitely() {
        splashScreen.setKeepOnScreenCondition { true }
    }

    /**
     * Keep splash screen on-screen for longer period. This is useful if you need to load data when
     * splash screen is appearing.
     */
    private fun keepSplashScreenFor5Seconds() {
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                Thread.sleep(3000)
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}