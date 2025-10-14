/*package com.example.pollapp.controllers;

import com.example.pollapp.domain.User;
import com.example.pollapp.dto.CreateUserDto;
import com.example.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final PollManager manager;

    public UsersController(PollManager manager) {
        this.manager = manager;
    }

    // Create user
    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserDto dto) {
        if (dto == null || dto.username == null || dto.username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        User u = manager.createUser(dto.username, dto.email);
        return ResponseEntity.created(URI.create("/api/users/" + u.getId())).body(u);
    }

    // List all users
    @GetMapping
    public Collection<User> list() {
        return manager.listUsers();
    }

    // Get one user
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return manager.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return manager.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
} */
