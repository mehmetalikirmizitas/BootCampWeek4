package com.example.bootcampWeek4.base

interface IBaseResponseHandler<T> {
    fun onSuccess(data: T)
    fun onFailure()
}