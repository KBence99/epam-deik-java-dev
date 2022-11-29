package com.epam.training.ticketservice.services;

public interface UserService {

    public Boolean signIn(String username, String password);

    public void signUp(String username, String password);
}
