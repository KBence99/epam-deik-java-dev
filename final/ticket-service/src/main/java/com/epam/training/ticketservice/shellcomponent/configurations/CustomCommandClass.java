package com.epam.training.ticketservice.shellcomponent.configurations;

import com.epam.training.ticketservice.account.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandClass {

    @Autowired
    protected UserHandler signInHandler;

    private Availability isAdmin() {
        if (signInHandler.isAdmin()) {
            return Availability.available();
        } else {
            return Availability.unavailable("not signed in as admin user");
        }
    }
}
