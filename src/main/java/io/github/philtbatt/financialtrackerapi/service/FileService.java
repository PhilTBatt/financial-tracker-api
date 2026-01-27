package io.github.philtbatt.financialtrackerapi.service;

import io.github.philtbatt.financialtrackerapi.model.Metrics;
import io.github.philtbatt.financialtrackerapi.model.Transaction;
import io.github.philtbatt.financialtrackerapi.model.TransactionRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final S3Client s3Client;
    private final String bucketName = "financial-files-bucket";

    public FileService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile file) {
        try {
            String id = UUID.randomUUID().toString();
            String key = id + "_" + file.getOriginalFilename();

            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, tempFile);

            Files.deleteIfExists(tempFile);

            return id;
        } catch (IOException e) {
            throw new RuntimeException("Upload failed", e);
        }
    }
}
