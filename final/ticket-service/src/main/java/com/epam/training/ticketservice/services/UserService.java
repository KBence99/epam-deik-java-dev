package com.epam.training.ticketservice.services;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public Boolean signIn(String username, String password);

    public Boolean signUp(String username, String password);
}
