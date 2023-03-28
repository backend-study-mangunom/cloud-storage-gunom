package com.example.myboxservice.controller

import com.example.myboxservice.service.FileUploadService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/upload")
class FileController(
    private val fileUploadService: FileUploadService,
) {
    @PostMapping
    fun upload(@RequestBody file: MultipartFile){
        fileUploadService.uploadFile(file)
    }
}