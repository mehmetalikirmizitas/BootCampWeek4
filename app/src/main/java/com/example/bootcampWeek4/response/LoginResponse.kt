package com.example.bootcampWeek4.response

import com.example.bootcampWeek4.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val user: User,
    val token : String
)
