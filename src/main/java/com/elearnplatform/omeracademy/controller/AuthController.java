package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.user.PasswordChangeDto;
import com.elearnplatform.omeracademy.dto.user.UserLoginDto;
import com.elearnplatform.omeracademy.dto.user.UserRegistrationDto;
import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.response.ApiResponse;
import com.elearnplatform.omeracademy.security.JwtUtil;
import com.elearnplatform.omeracademy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController
{
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(
            @Valid @RequestBody UserRegistrationDto registrationDto) {

        UserResponseDto user = userService.register(registrationDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful!", user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @Valid @RequestBody UserLoginDto loginDto) {

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user details
        User user = userService.getUserByUsername(loginDto.getUsername());

        // Generate JWT token
        String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(ApiResponse.success("You have successfully logged in.", jwt));
    }
    /*

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getCurrentUser(
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("No Authorized"));
        }

        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        UserResponseDto userDto = userService.getUserResponseDtoByUsername(username);

        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.success("You have successfully logged out."));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Object>> changePassword(
            @Valid @RequestBody PasswordChangeDto passwordDto,
            Authentication authentication) {

        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        userService.changePassword(user.getId(), passwordDto);

        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
    }

     */
}
