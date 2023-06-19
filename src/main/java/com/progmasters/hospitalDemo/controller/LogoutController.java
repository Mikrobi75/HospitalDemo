package com.progmasters.hospitalDemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logout")
@Slf4j
public class LogoutController {

    @PostMapping()
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        log.info("Http login, userName: " + request.getSession().getAttribute("userName"));
        request.getSession().invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
