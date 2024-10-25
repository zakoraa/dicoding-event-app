package com.raflis.final_submission_1_android_fundamental

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raflis.final_submission_1_android_fundamental.data.local.database.SettingPreferences
import com.raflis.final_submission_1_android_fundamental.data.local.database.dataStore
import com.raflis.final_submission_1_android_fundamental.databinding.ActivityMainBinding
import com.raflis.final_submission_1_android_fundamental.ui.settings.SettingsViewModel
import com.raflis.final_submission_1_android_fundamental.ui.settings.SettingsViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(this.application.dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        )[SettingsViewModel::class.java]


        settingsViewModel.getThemeSettings()
            .observe(this@MainActivity) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.eventDetailsFragment -> {
                    navView.visibility = View.GONE
                }

                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }

        navView.setupWithNavController(navController)

    }
}