package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.service.FileService;
import io.github.philtbatt.financialtrackerapi.service.DynamoDBService;
import io.github.philtbatt.financialtrackerapi.model.TransactionList;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class FileController {

    private final FileService fileService;
    private final DynamoDBService dynamoDBService;

    public FileController(FileService fileService, DynamoDBService dynamoDBService) {
        this.fileService = fileService;
        this.dynamoDBService = dynamoDBService;
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload a file")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        String key = fileService.upload(file);
        Map<String, String> response = Map.of("s3Key", key,"msg", "File uploaded");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get transaction list")
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionList> get(@PathVariable String id) {
        TransactionList item = dynamoDBService.getById(id);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<String> fetchFile(@PathVariable int id) {
        return ResponseEntity.ok("Getting File");
    }
}