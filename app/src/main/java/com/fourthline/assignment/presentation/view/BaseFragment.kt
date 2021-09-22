package com.fourthline.assignment.presentation.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.fourthline.assignment.domain.model.Event

/**
 * Created by Kadir Mert Özcan on 15-Sep-21.
 *
 * Base class for fragments. Applies operations that are common to all fragments, and
 * provides a way for fragments to access MainActivity
 */
abstract class BaseFragment<DataBindingClass : ViewDataBinding, ViewModelClass : ViewModel>
    : Fragment() {

    private val mainActivity by lazy {
        activity as MainActivity
    }

    private val navController by lazy {
        findNavController()
    }

    val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    val appCompatActivity: AppCompatActivity by lazy {
        activity as AppCompatActivity
    }

    // ViewDataBinding instance with the type parameter indicated by the child class
    lateinit var binding: DataBindingClass
        private set

    lateinit var viewModel: ViewModelClass
        private set

    // Layout res id for to inflate with data binding
    abstract val layoutId: Int

    // Must be set for providing type safe view model
    abstract val viewModelClass: Class<ViewModelClass>

    // Determines action bar visibility
    abstract val isActionBarVisible: Boolean

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity.actionBar.isVisible = isActionBarVisible

        // Applies type safe data binding
        binding = DataBindingUtil.inflate(
            inflater, layoutId, container, false) as DataBindingClass

        onViewBound()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(viewModelClass)

        // LiveData is observed by child classes in this method
        observeLiveData()
    }

    protected fun finishApplication() {
        mainActivity.finishAndRemoveTask()
    }

    // Called just before onCreateView is finished. Default implementation is empty
    open fun onViewBound() {}

    // Called just before onActivityCreated is finished
    open fun observeLiveData() {}


    /**
     * Inner class to be used by fragments for navigation purposes.
     * Sets the navigationEvent LiveData of MainViewModel, which is observed from the MainActivity
     */
    protected inner class FragmentNavigation {
        fun navigateFromHomeToSelfieFragment() {
            val navAction = HomeFragmentDirections
                .actionHomeFragmentToSelfieFragment()
            mainActivity.viewModel.setFragmentNavigationEvent(Event(navAction))
        }
        fun navigateFromSelfieToSelfieErrorFragment() {
            val navAction = SelfieFragmentDirections
                .actionSelfieFragmentToSelfieErrorFragment()
            mainActivity.viewModel.setFragmentNavigationEvent(Event(navAction))
        }
        fun navigateFromSelfieToSelfieResultsFragment(selfieUri: Uri) {
            val navAction = SelfieFragmentDirections
                .actionSelfieFragmentToSelfieResultsFragment(selfieUri.toString())
            mainActivity.viewModel.setFragmentNavigationEvent(Event(navAction))
        }
        fun navigateFromSelfieErrorToSelfieFragment() {
            val navAction = SelfieErrorFragmentDirections
                .actionSelfieErrorFragmentToSelfieFragment()
            mainActivity.viewModel.setFragmentNavigationEvent(Event(navAction))
        }
        fun navigateFromSelfieErrorToHomeFragment() {
            val navAction = SelfieErrorFragmentDirections
                .actionSelfieErrorFragmentToHomeFragment()
            mainActivity.viewModel.setFragmentNavigationEvent(Event(navAction))
        }
        fun navigateFromSelfieResultToSelfieFragment() {
            val navAction = SelfieResultsFragmentDirections
                .actionSelfieResultsFragmentToSelfieFragment()
            mainActivity.viewModel.setFragmentNavigationEvent(Event(navAction))
        }
        fun navigateToBack() {
            navController.popBackStack()
        }
    }

}