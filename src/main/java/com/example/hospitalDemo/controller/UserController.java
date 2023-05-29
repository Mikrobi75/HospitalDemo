package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.domain.User;
import com.example.hospitalDemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity registerUser(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        log.info("Http request, POST /api/users userName: " + userName + ", password: " + password);

        boolean profileWithUsernameAlreadyExists = userService.findByUsername(userName) != null;
        if (profileWithUsernameAlreadyExists) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userService.save(new User(userName, password));
            request.getSession().setAttribute("userName", userName);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

}
