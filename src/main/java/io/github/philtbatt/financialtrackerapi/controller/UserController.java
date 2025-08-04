package io.github.philtbatt.financialtrackerapi.controller;

import io.github.philtbatt.financialtrackerapi.model.UserData;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/user/upload")
    public UserData upload(@RequestBody UserData fileData) {
        return fileData;
    }

    @GetMapping("/user/")
    public UserData fetchUsers() {
        return new UserData();
    }

    @GetMapping("/user/{id}")
    public UserData fetchUser(@PathVariable int id) {
        return new UserData();
    }
}