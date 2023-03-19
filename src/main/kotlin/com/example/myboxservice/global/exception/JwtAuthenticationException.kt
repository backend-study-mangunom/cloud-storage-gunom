package com.example.myboxservice.global.exception

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(message: String?, cause: Throwable? = null) : AuthenticationException(message, cause)