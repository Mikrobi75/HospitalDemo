package com.example.hospitalDemo.service;

import com.example.hospitalDemo.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public boolean checkCredentials(String name, String password) {
        User byUsername = findByUsername(name);
        if (byUsername != null) {
            return byUsername.getPassword().equals(password);
        } else {
            return false;
        }
    }

    public User findByUsername(String username) {
        try {
            return entityManager
                    .createQuery(
                            "SELECT n FROM User n " +
                                    "WHERE n.username = :username",
                            User.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
