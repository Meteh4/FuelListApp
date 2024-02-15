package com.metoly.roomtest

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.metoly.roomtest.Fragments.CitySelectionFragment

class MainActivity : AppCompatActivity(), CitySelectionFragment.OnConfirmationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2500)
        installSplashScreen()
        setContentView(R.layout.activity_main)


        findViewById<ImageView>(R.id.dateIcon).setOnClickListener {
            navigateToFuelPriceListFragment()
        }
    }

    private fun navigateToFuelPriceListFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.navigate(R.id.fuelPriceListFragment)
    }

    override fun onConfirmation(city: String, district: String) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        val bundle = Bundle().apply {
            putString("city", city)
            putString("district", district)
        }
        navController.navigate(R.id.action_citySelectionFragment_to_priceScreenFragment, bundle)
    }
}
