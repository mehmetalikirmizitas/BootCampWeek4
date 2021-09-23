package com.example.bootcampWeek4.model

class User {
    private var email : String? = null
    var token : String? = null

    companion object{
        var user : User ?= null

        fun getCurrentInstance() : User {

            if(user == null){
                user = User()
            }
            return user!!
        }
    }

    fun setUser(loggedInUser: User){
        user?.email = loggedInUser.email
        user?.token = loggedInUser.token
    }

}