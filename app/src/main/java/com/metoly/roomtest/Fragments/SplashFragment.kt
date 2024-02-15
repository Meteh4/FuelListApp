package com.metoly.roomtest.Fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.metoly.roomtest.R

class SplashFragment : Fragment() {

    private val SPLASH_TIME_OUT: Long = 1500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.splash_fragment, container, false)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment2_to_citySelectionFragment)
        }, SPLASH_TIME_OUT)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

        return view
    }
}
