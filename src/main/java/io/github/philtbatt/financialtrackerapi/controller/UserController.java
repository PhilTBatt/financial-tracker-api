package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.model.UserData;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @PostMapping("/")
    public UserData upload(@RequestBody UserData fileData) {
        return fileData;
    }

    @GetMapping("/")
    public UserData fetchUsers() {
        return new UserData();
    }

    @GetMapping("/{id}")
    public UserData fetchUser(@PathVariable int id) {
        return new UserData();
    }
}