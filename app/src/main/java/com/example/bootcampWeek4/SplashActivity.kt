package com.example.bootcampWeek4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.model.User
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.utils.USER_TOKEN
import com.example.bootcampWeek4.utils.toast
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /**
         * Calling connection function
         */
        ServiceConnector.init()

        val intent = Intent(this@SplashActivity, MainActivity::class.java).apply {
            putExtra("isLoggedIn", isLoggedIn())
        }

        /**
         * Assign tokens to fetch user's tasks from database when user LoggedIn
         */
        if (isLoggedIn()) {
            User.getCurrentInstance().token = token
            ServiceConnector.restInterface.getUserLoginInfo()
                .enqueue(object : BaseCallBack<User>() {
                    override fun onSuccess(data: User) {
                        super.onSuccess(data)
                        User.getCurrentInstance().setUser(data)
                        User.getCurrentInstance().token = token
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                startActivity(intent)
                                finish()
                            }
                        }, 2000)
                    }

                    override fun onFailure() {
                        super.onFailure()
                        toast("Bir hata oluştu lütfen tekrar giriş yapınız")
                        finish()
                    }
                })
        } else {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    startActivity(intent)
                    finish()
                }
            }, 2000)
        }
    }

    /**
     * getting token information
     */
    private fun isLoggedIn(): Boolean = getToken().isNotEmpty()

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        token = sharedPreferences.getString(USER_TOKEN, "")!!
        return token!!
    }
}