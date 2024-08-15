package com.spyker3d.easyjob.root.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.databinding.ActivityRootBinding
import com.spyker3d.easyjob.search.ui.SearchRepeatHandler

class RootActivity : AppCompatActivity(), SearchRepeatHandler {
    private var doRepeat: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav_view)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filterRegionFragment, R.id.filterCountryFragment, R.id.filterIndustryFragment,
                R.id.filterPlaceToWorkFragment, R.id.jobDetailsFragment, R.id.filterSettingsFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setRepeat(doRepeat: Boolean) {
        this.doRepeat = doRepeat
    }

    override fun getRepeatBool(): Boolean {
        return doRepeat
    }
}
