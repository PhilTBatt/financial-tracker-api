package io.github.philtbatt.financialtrackerapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
            String key = "upload/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, tempFile);

            Files.deleteIfExists(tempFile);

            return "https://%s.s3.amazonaws.com/%s".formatted(bucketName, key);
        } catch (IOException e) {
            throw new RuntimeException("Upload failed", e);
        }
    }
}
