package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service = new UserServiceImpl();

    @Test
    void signInSuccess() {
        String username = "caesar";
        String password = "rome";

        when(repository.findByUsername("caesar")).thenReturn(new UserEntity(null,username, password));

        Boolean isLoggedIn = service.signIn(username, password);

        assertTrue(isLoggedIn);
    }

    @Test
    void signInFail() {
        String username = "caesar";
        String password = "rome";

        when(repository.findByUsername("caesar")).thenReturn(new UserEntity(null,username, "carthage"));

        Boolean isLoggedIn = service.signIn(username, password);

        assertFalse(isLoggedIn);
    }

    @Test
    void signUp(){

        UserEntity user = new UserEntity(null,"caesar","rome");

        service.signUp(user.getUsername(), user.getPassword());

        verify(repository).save(user);
    }
}