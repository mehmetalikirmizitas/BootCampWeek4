package com.example.bootcampWeek4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * getting isLoggedIn info from splashActivity of type boolean
         */
        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

        /**
         * Set first page as HomeFragment when user Logged in
         * Default First Page is LoginFragment
         */
        if (isLoggedIn) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                        as NavHostFragment

            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_graph)
            graph.startDestination = R.id.homeFragment
            navHostFragment.navController.graph = graph
        }
    }
}