package com.epam.training.ticketservice.Account;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Account {

    private AccountType type = AccountType.NOT_LOGGED_IN;
    private String accountName = "admin";

    public void LogIn(){
        setType(AccountType.ADMIN);
    }

    public void LogOut(){
        setType(AccountType.NOT_LOGGED_IN);
    }

    public String Describe(){
        if(getType().equals(AccountType.ADMIN)){
            return String.format("Signed in with privileged account %s",accountName);
        }
        else {
            return "You are not signed in";
        }
    }
}
