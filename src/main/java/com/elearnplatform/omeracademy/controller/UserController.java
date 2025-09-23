package com.elearnplatform.omeracademy.controller;

import com.elearnplatform.omeracademy.entity.User;
import com.elearnplatform.omeracademy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @RequestMapping("/")
    public ResponseEntity<List<User>> getUsers()
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }
}
