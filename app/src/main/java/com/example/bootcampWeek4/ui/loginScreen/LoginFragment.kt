package com.example.bootcampWeek4.ui.loginScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bootcampWeek4.R
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.response.LoginResponse
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.utils.USER_TOKEN
import com.example.bootcampWeek4.utils.getString
import com.example.bootcampWeek4.utils.saveDataAsString
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_login.setOnClickListener {
            loginFunction()
        }
    }

    private fun loginFunction() {
        val email= edittext_email.getString()
        val password = edittext_password.getString()

        val params = mutableMapOf<String,Any>().apply {
            put("email",email)
            put("password",password)
        }
        ServiceConnector.restInterface.login(params).enqueue(object : BaseCallBack<LoginResponse>(){
            override fun onSuccess(data: LoginResponse) {
                super.onSuccess(data)
                saveDataAsString(USER_TOKEN,data.token)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }

            override fun onFailure() {
                super.onFailure()
            }
        })
    }
}