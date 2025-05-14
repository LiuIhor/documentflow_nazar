package com.company.docflow.controller;

import com.company.docflow.dto.DocumentDto;
import com.company.docflow.dto.DocumentUpdateDto;
import com.company.docflow.dto.UserDto;
import com.company.docflow.model.Document;
import com.company.docflow.model.User;
import com.company.docflow.service.DocumentService;
import com.company.docflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search/byLogin")
    public  ResponseEntity<UserDto> getUserByLogin(@RequestParam String login) {
        UserDto user = userService.getUserByLogin(login);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{login}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String login,
                                                      @RequestBody UserDto updateUser) {
        UserDto updatedUser = userService.updateUser(login, updateUser);
        return ResponseEntity.ok(updatedUser);
    }
}
