package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.services.ScreeningService;
import com.epam.training.ticketservice.shellcomponent.configurations.CustomCommandClass;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands extends CustomCommandClass {
    private ScreeningService screeningService;

    @ShellMethod(value = "Creating a room", key = "create screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void createScreening(String movieName, String roomName, String screeningStart) {
        screeningService.addScreening(new ScreeningDT(movieName, roomName, screeningStart));
    }

    @ShellMethod(value = "Deleting a room", key = "delete screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void deleteScreening(String movieName, String roomName, String screeningStart) {
        screeningService.deleteScreening(new ScreeningDT(movieName, roomName, screeningStart));
    }

    @ShellMethod(value = "Listing rooms", key = "list screenings")
    public String listScreening() {
        return screeningService.listScreenings();
    }
}
