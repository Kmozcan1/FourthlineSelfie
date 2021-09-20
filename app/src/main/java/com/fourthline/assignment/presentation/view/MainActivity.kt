package com.fourthline.assignment.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fourthline.assignment.R
import com.fourthline.assignment.databinding.ActivityMainBinding
import com.fourthline.assignment.presentation.viewmodel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
    }

    private fun setViews() {
        setContentView(R.layout.activity_main)
        actionBar.setupWithNavController(navController, appBarConfiguration)
    }

    // Returns the fragment that is currently on the screen
    private fun getActiveFragment(): Fragment? {
        return supportFragmentManager.fragments
            .first()?.childFragmentManager?.fragments?.get(0)
    }
}