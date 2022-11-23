package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.services.BookService;
import com.epam.training.ticketservice.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    private AccountType type = AccountType.NOT_LOGGED_IN;

    private String userName = "";

    public static final String unauthorized = "Unauthorized for this command";

    public void signUp(String username, String password) {
        userService.signUp(username,password);
    }

    public void logInAdmin(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            setType(AccountType.ADMIN);
            setUserName("admin");
        } else {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    public void logInUser(String username, String password) {
        if (userService.signIn(username, password)) {
            setType(AccountType.USER);
            setUserName(username);
        } else {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    public void logOut() {
        setUserName("");
        setType(AccountType.NOT_LOGGED_IN);
    }

    public void describe() {
        if (getType().equals(AccountType.ADMIN)) {
            System.out.printf("Signed in with privileged account '%s'%n", userName);
        } else if (getType().equals(AccountType.USER)) {
            System.out.println(String.format("Signed in with account '%s'", userName));
            String bookings = bookService.getBooking(userName);
            if (bookings.equals("")) {
                System.out.println("You have not booked any tickets yet");
            } else {
                System.out.println("Your previous bookings are");
                System.out.println(bookings);
            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    public Boolean isAdmin() {
        return getType().equals(AccountType.ADMIN);
    }

    public Boolean isLoggedIn() {
        return getType().equals(AccountType.USER);
    }
}
