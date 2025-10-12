package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.user.PasswordChangeDto;
import com.elearnplatform.omeracademy.dto.user.UserLoginDto;
import com.elearnplatform.omeracademy.dto.user.UserRegistrationDto;
import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.exception.ResourceNotFoundException;
import com.elearnplatform.omeracademy.mapper.UserMapper;
import com.elearnplatform.omeracademy.response.ApiResponse;
import com.elearnplatform.omeracademy.security.JwtUtil;
import com.elearnplatform.omeracademy.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserMapper userMapper;
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

    @PreAuthorize("isAuthenticated()") // ✅ يمكن لأي مستخدم مسجل الخروج فقط إذا كان مسجلًا أصلاً
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response)
    {
        HttpSession session = request.getSession(false); // احصل على الجلسة إن وجدت
        if (session != null) {
            session.invalidate(); // حذف الجلسة
        }

        // حذف الكوكيز لو موجودة
        var cookies = request.getCookies();
        if (cookies != null) {
            for (var cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        return ResponseEntity.ok("Logged out successfully.");
    }

    @PreAuthorize("isAuthenticated()") // ✅ المستخدم لازم يكون مسجل دخول
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourceNotFoundException("User is not authenticated");
        }

        String username = authentication.getName(); // اسم المستخدم من الـ Security Context
        User user = userService.getUserByUsername(username);
        UserResponseDto userDto = userMapper.toUserResponseDto(user);

        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

/*
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
