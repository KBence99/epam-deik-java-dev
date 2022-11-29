package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService service = new UserServiceImpl(userRepository);

    @Test
    void signInSuccess() {
        String username = "caesar";
        String password = "rome";

        when(userRepository.findByUsername("caesar")).thenReturn(Optional.of(new UserEntity(null,username, "rome")));

        Boolean isLoggedIn = service.signIn(username, password);

        assertTrue(isLoggedIn);
    }

    @Test
    void signInFail() {
        String username = "caesar";
        String password = "rome";

        when(userRepository.findByUsername("caesar")).thenReturn(Optional.of(new UserEntity(null,"caesar","carthage")));

        Boolean isLoggedIn = service.signIn(username, password);

        assertFalse(isLoggedIn);
    }

    @Test
    void signUp(){

        UserEntity user = new UserEntity(null,"caesar","rome");

        service.signUp(user.getUsername(), user.getPassword());

        verify(userRepository).save(user);
    }
}