package com.fourthline.assignment.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.ActivityMainBinding
import com.fourthline.assignment.domain.model.Event
import com.fourthline.assignment.presentation.viewmodel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val DEFAULT_PERMISSION_REQUEST_CODE = 10
        private const val CAMERA_PROCEED_PERMISSION_REQUEST_CODE = 11
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
        private val REQUIRED_PERMISSIONS = arrayOf(CAMERA_PERMISSION)
    }

    var queuedNavigationDirection: NavDirections? = null

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment : NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val navGraph: NavGraph by lazy {
        navController.navInflater.inflate(R.navigation.nav_graph)
    }

    val actionBar: MaterialToolbar by lazy {
        findViewById(R.id.topAppBar)
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FourthlineSelfie)
        super.onCreate(savedInstanceState)
        setViews()
        checkPermissions()
        viewModel.fragmentNavigationEvent.observe(this, observeFragmentNavigation())
    }

    // Observer method for fragmentNavigationEvent LiveData. Handles fragment navigation.
    private fun observeFragmentNavigation() = Observer<Event<NavDirections>> { navEvent ->
        navEvent.getContentIfNotHandled()?.let { navDirections ->
            when(navDirections.actionId) {
                R.id.action_homeFragment_to_selfieFragment -> {
                    queuedNavigationDirection = navDirections
                    navigateWithCameraPermission()
                    actionBar.isVisible = false
                }
                R.id.action_selfieFragment_to_selfieErrorFragment,
                R.id.action_selfieErrorFragment_to_homeFragment -> {
                    //actionBar.isVisible = true
                    navController.navigate(navDirections)
                }
                R.id.action_selfieErrorFragment_to_selfieFragment,
                R.id.action_selfieResultsFragment_to_selfieFragment -> {
                    actionBar.isVisible = false
                    navController.navigate(navDirections)
                }
                R.id.action_selfieFragment_to_selfieResultsFragment -> {
                    val selfieUri = navDirections.arguments.getString("selfieUri")
                    selfieUri?.let {
                        navController.navigate(navDirections)
                    }
                }
            }
        }
    }

    // Navigates to the queued fragment if the camera permission is granted
    // Asks for permission if not
    private fun navigateWithCameraPermission(calledAfterPermissionResult: Boolean = false) {
        if (ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION)
            == PackageManager.PERMISSION_GRANTED) {
            queuedNavigationDirection?.let { navDirection ->
                navController.navigate(navDirection)
            }
            queuedNavigationDirection = null
        } else {
            // To avoid infinite call if user keeps denying for some reason
            if (!calledAfterPermissionResult) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(CAMERA_PERMISSION),
                    CAMERA_PROCEED_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    // Callback method that is invoked after user responds to the permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            //
            CAMERA_PROCEED_PERMISSION_REQUEST_CODE ->
                navigateWithCameraPermission(true)
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    // View related stuff goes here
    private fun setViews() {
        setContentView(R.layout.activity_main)
        actionBar.setupWithNavController(navController, appBarConfiguration)
    }

    // Checks the granted permissions and ask for them if they aren't granted yet
    private fun checkPermissions() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, DEFAULT_PERMISSION_REQUEST_CODE)
        }
    }

    // Returns the fragment that is currently on the screen
    private fun getActiveFragment(): Fragment? {
        return supportFragmentManager.fragments
            .first()?.childFragmentManager?.fragments?.get(0)
    }

    // Checks if the permission is granted for all permissions in the REQUIRED_PERMISSIONS array
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

}