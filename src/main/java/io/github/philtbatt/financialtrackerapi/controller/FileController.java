package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/file")
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload a file")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        fileService.upload(file);
        Map<String, String> response = Map.of("msg","File uploaded");
        return ResponseEntity.ok(response);
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