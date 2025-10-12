package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.user.PasswordChangeDto;
import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.dto.user.UserUpdateDto;
import com.elearnplatform.omeracademy.entity.Role;
import com.elearnplatform.omeracademy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController
{
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN') or #principal.username == userService.getUserByUsername(userUpdateDto.username).username")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or #username == principal.username")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserResponseDtoByUsername(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody PasswordChangeDto passwordChangeDto,
            Authentication authentication) {

        String username = authentication.getName();
        var user = userService.getUserByUsername(username);
        userService.changePassword(user.getId(), passwordChangeDto);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/students")
    public ResponseEntity<Long> getTotalStudentsCount() {
        return ResponseEntity.ok(userService.getTotalStudentsCount());
    }


}
