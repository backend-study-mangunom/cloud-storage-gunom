package com.example.myboxservice.domain.user.repository

import com.example.myboxservice.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}