package com.example.myboxservice.service

import com.example.myboxservice.domain.user.User
import com.example.myboxservice.domain.user.repository.UserRepository
import com.example.myboxservice.global.enums.ExceptionMessage
import com.example.myboxservice.global.exception.UserDuplicateException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) : UserService {

    @Throws(UserDuplicateException::class)
    override fun saveUser(email: String, password: String) {
        val exUser = userRepository.findByEmail(email)
        if (exUser != null) throw UserDuplicateException(ExceptionMessage.USER_DUPLICATE_EXCEPTION.message)
        userRepository.save(User(email = email, password = passwordEncoder.encode(password)))
    }

    override fun findById(id: Long): User {
        return userRepository.findById(id).orElseGet { throw java.util.NoSuchElementException() }
    }
}