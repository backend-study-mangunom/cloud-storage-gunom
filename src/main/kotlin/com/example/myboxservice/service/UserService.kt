package com.example.myboxservice.service

import com.example.myboxservice.domain.user.User

interface UserService {
    fun saveUser(email: String, password: String)
    fun findById(id: Long): User
}