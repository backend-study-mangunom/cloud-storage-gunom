package com.example.myboxservice.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.text.DecimalFormat


@Service
class FIleUploadServiceImpl(
    private val transferManager: TransferManager,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) : FileUploadService {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun uploadFile(file: MultipartFile): SseEmitter {
        val fileName = file.originalFilename
        val metadata = ObjectMetadata().apply {
            contentLength = file.size
        }
        val request = PutObjectRequest(bucket, fileName, file.inputStream, metadata)

        val emitter = SseEmitter()

        Thread {
            val fileUpload = transferManager.upload(request)
            while (!fileUpload.isDone) {
                try {
                    val progress = fileUpload.progress.percentTransferred
                    val decimalFormat = DecimalFormat("##0.00")
                    emitter.send(
                        SseEmitter.event().name("upload").data("Upload progress: ${decimalFormat.format(progress)}%")
                    )
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    // handle exception
                }
            }
            try {
                emitter.send(SseEmitter.event().name("upload").data("Upload complete"))
                emitter.complete()
            } catch (e: IOException) {
                // handle exception
            }
            log.info("[DONE] upload success")
        }.start()

        return emitter
    }
}