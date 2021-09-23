package com.example.bootcampWeek4

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.model.Task
import com.example.bootcampWeek4.response.TaskResponse
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.utils.toast

class MainActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

        if (isLoggedIn){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                    as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_graph)
            graph.startDestination = R.id.homeFragment
            navHostFragment.navController.graph = graph
        }
    }
}