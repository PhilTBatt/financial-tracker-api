package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.model.FileData;
import io.github.philtbatt.financialtrackerapi.model.UploadResponseData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @PostMapping("/files/upload")
    public UploadResponseData upload(@RequestBody MultipartFile file) {
        return new UploadResponseData("Upload successful.");
    }

    @GetMapping("/files/")
    public FileData fetchFiles() {
        return new FileData("Placeholder");
    }

    @GetMapping("/files/{id}")
    public FileData fetchFile(@PathVariable int id) {
        return new FileData("Placeholder");
    }
}