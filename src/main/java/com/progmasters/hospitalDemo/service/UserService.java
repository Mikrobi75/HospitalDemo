package com.progmasters.hospitalDemo.service;

import com.progmasters.hospitalDemo.domain.User;
import com.progmasters.hospitalDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean checkCredentials(String name, String password) {
        return userRepository.checkCredentials(name, password);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
