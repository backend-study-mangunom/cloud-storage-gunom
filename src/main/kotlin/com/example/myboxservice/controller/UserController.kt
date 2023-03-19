package com.example.myboxservice.controller

import com.example.myboxservice.controller.dto.ApiResponse
import com.example.myboxservice.controller.dto.SignUpRequestDto
import com.example.myboxservice.global.enums.ExceptionMessage
import com.example.myboxservice.global.exception.UserDuplicateException
import com.example.myboxservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpRequestDto: SignUpRequestDto): ResponseEntity<ApiResponse<Nothing>> {
        userService.saveUser(signUpRequestDto.email, signUpRequestDto.password)
        return ResponseEntity.ok().body(ApiResponse.success(null, "SignUp Success"))
    }

    @ExceptionHandler(UserDuplicateException::class)
    fun handlingUserDuplicate(e: UserDuplicateException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.badRequest().body(ApiResponse.error(ExceptionMessage.USER_DUPLICATE_EXCEPTION.message))
    }
}