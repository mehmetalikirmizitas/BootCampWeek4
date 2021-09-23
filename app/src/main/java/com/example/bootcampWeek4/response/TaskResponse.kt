package com.example.bootcampWeek4.response

import com.example.bootcampWeek4.model.Task
import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("data")
    val task: ArrayList<Task>,
    val count: Int
)