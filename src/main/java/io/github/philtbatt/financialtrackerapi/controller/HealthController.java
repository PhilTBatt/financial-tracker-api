package io.github.philtbatt.financialtrackerapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://philtbatt.github.io", "http://localhost:4200"})
@RestController
class HealthController {
    @GetMapping("/health")
    String health() {
        return "ok";
    }
}
