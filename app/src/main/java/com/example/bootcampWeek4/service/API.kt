package com.example.bootcampWeek4.service

import com.example.bootcampWeek4.model.CompletedTaskRequest
import com.example.bootcampWeek4.model.Task
import com.example.bootcampWeek4.model.TaskRequest
import com.example.bootcampWeek4.model.User
import com.example.bootcampWeek4.response.LoginResponse
import com.example.bootcampWeek4.response.TaskResponse
import retrofit2.Call
import retrofit2.http.*

interface API {

    @POST("user/login")
    fun login(@Body params: MutableMap<String, Any>): Call<LoginResponse>

    @GET("user/me")
    fun getUserLoginInfo(): Call<User>

    @POST("task")
    fun addNewTask(@Body params: TaskRequest): Call<Task>

    @GET("task")
    fun getTaskByPagination(@Query("limit") limit : Int, @Query("skip") skip:Int) : Call<TaskResponse>

    @DELETE("task/{id}")
    fun deleteTaskById(@Path("id") id: String): Call<Task>

    @PUT("task/{id}")
    fun updateTaskById(@Path("id") id: String,@Body completed : CompletedTaskRequest): Call<Task>

}