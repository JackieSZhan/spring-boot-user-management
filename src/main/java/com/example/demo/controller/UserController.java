package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request){
        UserResponseDto created = userService.createUser(request);
        URI location = URI.create("api/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDto> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchByEmail(keyword));
    }

    /**
     * GET /api/users/status?active=true
     * @RequestParam with a default value if the client omits it entirely.
     */
    @GetMapping("/status")
    public ResponseEntity<List<UserResponseDto>> getUsersByStatus(
            @RequestParam(defaultValue = "true") Boolean status) {
        return ResponseEntity.ok(userService.getUsersByStatus(status));
    }

    /**
     * PUT /api/users/{id}
     * Idempotent - replaces the ENTIRE user resource with the given data.
     * Calling this 3 times with the same body leaves the user in the same
     * final state as calling it once.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequestDto request) {
        UserResponseDto updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Patch /api/users/{id}
     * Update User's status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDto> updateUserStatus(
            @PathVariable Long id, @RequestParam Boolean status) {
        UserResponseDto updated = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/users/{id}
     * Idempotent - deleting an already-deleted (or never-existing) user
     * has the same end state as deleting it once.
     * Returns 204 No Content - success, but nothing meaningful to return.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // 204
    }


}
