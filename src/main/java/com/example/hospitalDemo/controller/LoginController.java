package com.example.hospitalDemo.controller;

import com.example.hospitalDemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService profileService) {
        this.userService = profileService;
    }

    @PostMapping
    public ResponseEntity<Void> login(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        boolean isValid = userService.checkCredentials(userName, password);
        log.info("Http login, userName: " + userName + ", password: " + password);

        if (isValid) {
            request.getSession().setAttribute("userName", userName);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
