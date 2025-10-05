package com.elearnplatform.omeracademy.service;

import com.elearnplatform.omeracademy.dto.user.PasswordChangeDto;
import com.elearnplatform.omeracademy.dto.user.UserRegistrationDto;
import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.dto.user.UserUpdateDto;
import com.elearnplatform.omeracademy.entity.GradeLevel;
import com.elearnplatform.omeracademy.entity.Role;
import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.exception.ResourceAlreadyExistsException;
import com.elearnplatform.omeracademy.exception.ResourceNotFoundException;
import com.elearnplatform.omeracademy.mapper.UserMapper;
import com.elearnplatform.omeracademy.repository.GradeLevelRepository;
import com.elearnplatform.omeracademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final GradeLevelRepository gradeLevelRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto register(UserRegistrationDto userRegistrationDto)
    {
        if(userRepository.existsByUsername(userRegistrationDto.getUsername()))
            throw new ResourceAlreadyExistsException("Username already used");

        User user = userMapper.toUserEntity(userRegistrationDto);
        user.setPasswordHash(passwordEncoder.encode(userRegistrationDto.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(savedUser);

    }

    // Update user
    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is Not Found"));

        /*
        // Check if email is being changed and if it's already in use
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateResourceException("البريد الإلكتروني مستخدم بالفعل");
            }
        }
        */

        // Check if ((username)) is being changed and if it's already in use
        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userUpdateDto.getUsername())) {
                throw new ResourceAlreadyExistsException("Username already used");
            }
            user.setUsername(userUpdateDto.getUsername());
        }

        // Check if ((phone)) is being changed and if it's already in use
        if (userUpdateDto.getPhone() != null && !userUpdateDto.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(userUpdateDto.getPhone())) {
                throw new ResourceAlreadyExistsException("Phone already used");
            }
            user.setPhone(userUpdateDto.getPhone());
        }

        // Update grade level if provided
        if (userUpdateDto.getGradeLevelId() != null) {
            GradeLevel gradeLevel = gradeLevelRepository.findById(userUpdateDto.getGradeLevelId())
                    .orElseThrow(() -> new ResourceNotFoundException("GradeLevel is Not Found"));
            user.setGradeLevel(gradeLevel);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(updatedUser);
    }

    // Delete user
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User is Not Found");
        }
        userRepository.deleteById(id);
    }

    // Get user by username
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User is Not Found"));
    }

    // Get all users
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponseDtoList(users);
    }

    // Get users by role
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersByRole(Role role) {
        List<User> users = userRepository.findByRole(role);
        return userMapper.toUserResponseDtoList(users);
    }

    // Get students by grade level
    @Transactional(readOnly = true)
    public List<UserResponseDto> getStudentsByGradeLevel(Integer gradeLevelId) {
        List<User> students = userRepository.findByRoleAndGradeLevelId(Role.STUDENT, gradeLevelId);
        return userMapper.toUserResponseDtoList(students);
    }

    // Change password
    @Transactional
    public void changePassword(Long userId, PasswordChangeDto passwordChangeDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found"));

        // Verify current password
        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password does not Correct");
        }

        // Verify new password confirmation
        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password does not match confirm password");
        }

        // Update password
        user.setPasswordHash(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);
    }

    // Get total students count
    @Transactional(readOnly = true)
    public long getTotalStudentsCount() {
        return userRepository.findByRole(Role.STUDENT).size();
    }


}
