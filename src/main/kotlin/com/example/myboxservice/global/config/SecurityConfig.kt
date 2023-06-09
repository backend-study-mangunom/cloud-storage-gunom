package com.example.myboxservice.global.config

import com.example.myboxservice.global.auth.CustomAuthenticationProvider
import com.example.myboxservice.global.auth.JwtAuthenticationEntryPoint
import com.example.myboxservice.global.auth.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationProvider: CustomAuthenticationProvider,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
){

    @Bean
    @Throws(java.lang.Exception::class)
    fun authManager(http: HttpSecurity): AuthenticationManager? {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.authenticationProvider(authenticationProvider)
        return authenticationManagerBuilder.build()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/login", "/signUp", "/upload")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling().authenticationEntryPoint(JwtAuthenticationEntryPoint())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .build()
    }
}