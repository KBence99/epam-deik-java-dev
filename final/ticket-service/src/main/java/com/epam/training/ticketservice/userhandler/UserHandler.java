package com.epam.training.ticketservice.userhandler;

import com.epam.training.ticketservice.services.BookService;
import com.epam.training.ticketservice.services.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Data
@Component
@NoArgsConstructor
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

    public String describe() {
        StringJoiner describe = new StringJoiner("\n");
        if (getType().equals(AccountType.ADMIN)) {
            describe.add(String.format("Signed in with privileged account '%s'",userName));
        } else if (getType().equals(AccountType.USER)) {
            describe.add(String.format("Signed in with account '%s'", userName));
            String bookings = bookService.getBooking(userName);
            if (bookings.equals("")) {
                describe.add("You have not booked any tickets yet");
            } else {
                describe.add("Your previous bookings are");
                describe.add(bookings);
            }
        } else {
            describe.add("You are not signed in");
        }
        return describe.toString();
    }

    public Boolean isAdmin() {
        return getType().equals(AccountType.ADMIN);
    }

    public Boolean isUser() {
        return getType().equals(AccountType.USER);
    }
}