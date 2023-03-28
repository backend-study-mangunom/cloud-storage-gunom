package com.example.myboxservice.service

import org.springframework.web.multipart.MultipartFile

interface FileUploadService {
    fun uploadFile(file: MultipartFile)
}