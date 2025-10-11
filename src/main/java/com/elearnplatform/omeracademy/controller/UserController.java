package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.dto.user.UserResponseDto;
import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController
{

    private final UserService userService;


}
