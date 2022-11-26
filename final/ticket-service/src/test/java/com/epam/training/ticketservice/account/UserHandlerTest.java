package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.services.BookService;
import com.epam.training.ticketservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    private final String username = "caesar";
    private final String password = "rome";
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @InjectMocks
    UserHandler handler;

    @BeforeEach
    void setup(){
    }

    @Test
    void adminLoginTest(){
        handler.logInAdmin("admin","admin");
        assertTrue(handler.isAdmin());
    }

    @Test
    void adminLongWrongPassword(){
        handler.logInAdmin("admin","asd");
        assertFalse(handler.isAdmin());
    }
    @Test
    void adminLongWrongName(){
        handler.logInAdmin("caesar","admin");
        assertFalse(handler.isAdmin());
    }

    @Test
    void loginUserTest(){

        //When
        when(userService.signIn(username, password)).thenReturn(true);

        //Then
        handler.logInUser(username,password);

        assertTrue(handler.isUser());
    }

    @Test
    void loginUserFailedTest(){

        //When
        when(userService.signIn("asd", password)).thenReturn(false);

        //Then
        handler.logInUser("asd",password);

        assertFalse(handler.isUser());
    }

    @Test
    void logOutTest(){

        //When
        when(userService.signIn(username, password)).thenReturn(true);

        //Then
        handler.logInUser(username,password);
        handler.logOut();
        assertFalse(handler.isUser());
    }

    @Test
    void signUpTest(){

        handler.signUp(username,password);
        verify(userService).signUp(username,password);
    }

//Describe tests

    @Test
    void describeAdminTest(){

        System.setOut(new PrintStream(outputStreamCaptor));

        handler.logInAdmin("admin","admin");
        handler.describe();

        String expected = "Signed in with privileged account 'admin'";
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }

    @Test
    void describeUserTest(){

        System.setOut(new PrintStream(outputStreamCaptor));

        when(userService.signIn(username, password)).thenReturn(true);
        when(bookService.getBooking(username)).thenReturn("");

        handler.logInUser(username,password);
        handler.describe();

        String expected = "Signed in with account 'caesar'\n" +
                "You have not booked any tickets yet";
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }

    @Test
    void describeNonLoggedInTest(){

        System.setOut(new PrintStream(outputStreamCaptor));

        handler.describe();

        String expected = "You are not signed in";
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }

    @Test
    void describeWithBooking(){

        System.setOut(new PrintStream(outputStreamCaptor));

        String booking = "Booked tickets";

        when(userService.signIn(username, password)).thenReturn(true);
        when(bookService.getBooking(username)).thenReturn(booking);

        handler.logInUser(username,password);
        handler.describe();

        String expected = "Signed in with account 'caesar'\n" +
                "Your previous bookings are\n" + booking;
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }
}