package com.example.myboxservice.global.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig(
    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKey: String,
    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretKey: String,
    @Value("\${cloud.aws.credentials.region}")
    private val region: String,
) {
    @Bean
    fun credentialsProvider(): BasicAWSCredentials {
        return BasicAWSCredentials(accessKey, secretKey)
    }


    @Bean
    fun s3Client(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentialsProvider()))
            .withRegion(region)
            .build()
    }

    @Bean
    fun transferManager(): TransferManager {
        return TransferManagerBuilder.standard()
            .withS3Client(s3Client())
            .build()
    }
}