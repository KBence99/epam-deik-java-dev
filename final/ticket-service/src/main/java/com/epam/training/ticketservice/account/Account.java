package com.epam.training.ticketservice.account;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Account {

    private AccountType type = AccountType.NOT_LOGGED_IN;
    private String accountName = "admin";

    public static final String unathorized = "Unauthorized for this command";

    public void logIn() {
        setType(AccountType.ADMIN);
    }

    public void logOut() {
        setType(AccountType.NOT_LOGGED_IN);
    }

    public String describe() {
        if (getType().equals(AccountType.ADMIN)) {
            return String.format("Signed in with privileged account '%s'",accountName);
        } else {
            return "You are not signed in";
        }
    }

    public Boolean isLoggedIn() {
        return getType().equals(AccountType.ADMIN);
    }
}
