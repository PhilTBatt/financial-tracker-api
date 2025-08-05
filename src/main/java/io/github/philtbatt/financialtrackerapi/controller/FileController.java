package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/file")
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        String url = fileService.upload(file);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/")
    public ResponseEntity<String> fetchFiles() {
        return ResponseEntity.ok("Getting Files");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> fetchFile(@PathVariable int id) {
        return ResponseEntity.ok("Getting File");
    }
}