package com.hamza.ecommerce.utils;

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.hamza.ecommerce.BR
import com.hamza.ecommerce.data.repository.common.AppDataStoreRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppPreferenceRepository
import com.hamza.ecommerce.ui.common.customviews.ProgressDialog
import dagger.Binds
import javax.inject.Singleton


abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment() {

    val progressDialog by lazy { ProgressDialog.createProgressDialog(requireActivity()) }

    protected abstract val viewModel: VM
    protected var _binding: DB? = null
    protected val binding get() = _binding!!

    var myContext: Context? = null
    var myView: View? = null
    var myActivity: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    /**
     * Get layout resource id which inflate in onCreateView.
     */
    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doDataBinding()
        init()
    }


    /**
     * Do your other stuff in init after binding layout.
     */
    abstract fun init()

    private fun doDataBinding() {
        // it is extra if you want to set life cycle owner in binding
        binding.lifecycleOwner = viewLifecycleOwner
        // Here your viewModel and binding variable imlementation
        binding.setVariable(
            BR.viewmodel, viewModel //BR.viewmodel, viewModel
        )  // In all layout the variable name should be "viewModel"
        binding.executePendingBindings()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ${binding.javaClass.name}")
        super.onDestroyView()
        _binding = null
    }

    protected fun navigate(navDirections: NavDirections?) {
        Navigation.findNavController(myView!!).navigate(navDirections!!)
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}