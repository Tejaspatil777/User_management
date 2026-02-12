package com.example.usermanagement.controller;

import com.example.usermanagement.dtos.requestDto.UserRequestDto;
import com.example.usermanagement.dtos.responseDto.UserResponseDTO;
import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE USEr
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestParam String roleName) {
        return ResponseEntity.ok(userService.create(user, roleName));
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public User update(@PathVariable Long id,
                       @RequestBody UserRequestDto dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return userService.update(id, user, dto.getRoleName());
    }

    //  DELETE USER
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "User deleted successfully";
    }
}
