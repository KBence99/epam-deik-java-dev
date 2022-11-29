package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Boolean signIn(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new RuntimeException("No user by this name found");
        });
        return user.getPassword().equals(password);
    }

    @Override
    public void signUp(String username, String password) {
        UserEntity user = new UserEntity(null,username,password);
        userRepository.save(user);
    }
}
