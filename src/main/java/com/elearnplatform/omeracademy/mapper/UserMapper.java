package com.elearnplatform.omeracademy.mapper;

import com.elearnplatform.omeracademy.dto.user.UserRegistrationDto;
import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.entity.GradeLevel;
import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.repository.GradeLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper
{
    private final GradeLevelRepository gradeLevelRepository;

    public User toUserEntity(UserRegistrationDto userRegistrationDto)
    {
        GradeLevel gradeLevel = gradeLevelRepository.findById(userRegistrationDto.getGradeLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid grade level"));

        User user = new User();
        user.setFullName(userRegistrationDto.getFullName());
        user.setUsername(userRegistrationDto.getUsername());
        user.setBirthday(userRegistrationDto.getBirthday());
        user.setPhone(userRegistrationDto.getPhone());
        user.setGradeLevel(gradeLevel);
        return user;
    }

    public UserResponseDto toUserResponseDto(User user)
    {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setBirthday(user.getBirthday());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setGradeLevelName(user.getGradeLevel().getGradeLevelName());
        userResponseDto.setRole(user.getRole().name());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        return userResponseDto;
    }

    public List<UserResponseDto> toUserResponseDtoList(List<User> users)
    {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users)
            userResponseDtos.add(toUserResponseDto(user));
        return userResponseDtos;
    }
}
