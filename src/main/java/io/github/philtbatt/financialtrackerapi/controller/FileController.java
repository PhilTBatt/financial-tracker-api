package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.model.FileData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @PostMapping("/upload")
    public FileData upload(@RequestBody FileData fileData) {
        return fileData;
    }
}