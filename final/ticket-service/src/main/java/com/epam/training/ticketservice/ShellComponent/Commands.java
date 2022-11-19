package com.epam.training.ticketservice.ShellComponent;

import com.epam.training.ticketservice.Account.Account;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class Commands {

    private Account account;

    @ShellMethod(value = "Signs in admin",key = "sign in privileged")
    public String login(String username, String password){
        if(username.equals("admin") && password.equals("admin"))
            account.LogIn();
        else
            return "Login failed due to incorrect credentials";
        return String.format("Signing in as %s",username);
    }

    @ShellMethod(value = "Signs out",key = "sign out")
    public String login(){
        account.LogOut();
        return "Signing out";
    }

    @ShellMethod(value = "Description of account", key = "describe account")
    public String describe(){
        return account.Describe();
    }
}
