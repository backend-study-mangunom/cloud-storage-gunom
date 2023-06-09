package com.example.myboxservice.service

import com.example.myboxservice.domain.user.SecurityUser
import com.example.myboxservice.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.nio.file.attribute.UserPrincipalNotFoundException

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
        if (user != null) return SecurityUser(user)
        else throw UsernameNotFoundException("user not found!")
    }

    fun loadUserByUserId(id: Long): SecurityUser {
        val user = userRepository.findById(id).orElseThrow { throw UserPrincipalNotFoundException("User not exist") }
        return SecurityUser(user)
    }
}