package com.example.myboxservice.service

import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface FileUploadService {
    fun uploadFile(file: MultipartFile): SseEmitter
}