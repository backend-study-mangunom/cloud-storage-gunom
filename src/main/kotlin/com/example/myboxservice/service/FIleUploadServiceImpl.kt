package com.example.myboxservice.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.text.DecimalFormat

@Service
class FIleUploadServiceImpl(
    private val transferManager: TransferManager,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) : FileUploadService {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun uploadFile(file: MultipartFile){
        val fileName = file.originalFilename
        val metadata = ObjectMetadata().apply {
            contentLength = file.size
        }
        val request = PutObjectRequest(bucket, fileName, file.inputStream, metadata)
        val fileUpload = transferManager.upload(
            request
        )
        val decimalFormat = DecimalFormat("##0.00")
        while (!fileUpload.isDone) {
            log.info("Upload progress: ${decimalFormat.format(fileUpload.progress.percentTransferred)}")
            Thread.sleep(500)
        }
        log.info("[DONE] upload success")
    }
}