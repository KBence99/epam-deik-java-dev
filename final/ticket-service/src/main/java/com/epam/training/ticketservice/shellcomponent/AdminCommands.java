package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class AdminCommands {

    private Account account;

    private ApplicationContext applicationContext;

    @ShellMethod(value = "Signs in admin",key = "sign in privileged")
    public String login(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            account.logIn();
        } else {
            return "Login failed due to incorrect credentials";
        }
        return String.format("Signing in as %s",username);
    }

    @ShellMethod(value = "Signs out",key = "sign out")
    public String login() {
        account.logOut();
        return "Signing out";
    }

    @ShellMethod(value = "Description of account", key = "describe account")
    public String describe() {
        return account.describe();
    }
}
