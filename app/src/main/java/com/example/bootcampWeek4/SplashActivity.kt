package com.example.bootcampWeek4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.model.User
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.utils.USER_TOKEN
import com.example.bootcampWeek4.utils.startActivity
import com.example.bootcampWeek4.utils.toast

class SplashActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ServiceConnector.init()
        val intent = Intent(this@SplashActivity,MainActivity::class.java).apply {
            putExtra("isLoggedIn",isLoggedIn())
        }

        if (isLoggedIn()) {
            User.getCurrentInstance().token = token
            ServiceConnector.restInterface.getUserLoginInfo()
                .enqueue(object : BaseCallBack<User>() {
                    override fun onSuccess(data: User) {
                        super.onSuccess(data)

                        User.getCurrentInstance().setUser(data)
                        User.getCurrentInstance().token = token
                        startActivity(intent)
                    }
                    override fun onFailure() {
                        super.onFailure()
                        toast("Bir hata oluştu lütfen tekrar giriş yapınız")
                        startActivity(intent)
                    }
                })
        }
        else
            startActivity(intent)
    }

    private fun isLoggedIn(): Boolean = getToken().isNotEmpty()

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        token = sharedPreferences.getString(USER_TOKEN, "")!!
        Log.e("token is : ",token.toString())
        return token!!
    }
}