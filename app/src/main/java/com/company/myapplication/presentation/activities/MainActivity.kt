package com.company.myapplication.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.company.myapplication.databinding.ActivityMainBinding
import com.company.myapplication.helper.utils.loadFragment
import com.company.myapplication.presentation.fragments.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SplashScreen.OnExitAnimationListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { false }
        splashScreen.setOnExitAnimationListener(this)
    }

    override fun onSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        splashScreenViewProvider.remove()
        loadFragment(LoginFragment(), this)
    }
}