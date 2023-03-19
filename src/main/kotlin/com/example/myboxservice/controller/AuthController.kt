package com.example.myboxservice.controller

import com.example.myboxservice.controller.dto.ApiResponse
import com.example.myboxservice.controller.dto.LoginRequestDto
import com.example.myboxservice.controller.dto.LoginResponseDto
import com.example.myboxservice.global.auth.JwtTokenProvider
import com.example.myboxservice.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<*>? {
        return try {
            val authentication = authService.authenticate(loginRequestDto.email, loginRequestDto.password)
            val token: String = jwtTokenProvider.generateToken(authentication)
            ResponseEntity.ok(ApiResponse.success(LoginResponseDto(token), "login success"))
        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid username or password"))
        }
    }
}