package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Boolean signIn(String username, String password) {
        UserEntity user = repository.findByUsername(username);
        return user.getPassword().equals(password);
    }

    @Override
    public void signUp(String username, String password) {
        UserEntity user = new UserEntity(null,username,password);
        repository.save(user);
    }
}
