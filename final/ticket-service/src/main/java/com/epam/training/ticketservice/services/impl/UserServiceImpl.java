package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public Boolean signIn(String username, String password) {
        UserEntity user = repository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean signUp(String username, String password) {
        UserEntity user = new UserEntity(null,username,password);
        repository.save(user);
        return true;
    }
}
