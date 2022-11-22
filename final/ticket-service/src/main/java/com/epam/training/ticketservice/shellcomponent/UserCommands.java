package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.SignInHandler;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserCommands {

    private SignInHandler handler;

    @ShellMethod(value = "Signs in admin",key = "sign in privileged")
    public void loginAdmin(String username, String password) {
        handler.logInAdmin(username,password);
    }

    @ShellMethod(value = "Sign up new user", key = "sign up")
    public void signUp(String username, String password) {
        handler.signUp(username, password);
    }

    @ShellMethod(value = "Signs in user",key = "sign in")
    public void login(String username, String password) {
        handler.logInUser(username,password);
    }

    @ShellMethod(value = "Signs out",key = "sign out")
    public String login() {
        handler.logOut();
        return "Signing out";
    }

    @ShellMethod(value = "Description of account", key = "describe account")
    public void describe() {
        handler.describe();
    }
}
