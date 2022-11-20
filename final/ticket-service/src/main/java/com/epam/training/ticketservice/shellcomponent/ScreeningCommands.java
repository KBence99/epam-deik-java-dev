package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.services.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private Account account;
    private ScreeningService screeningService;

    @ShellMethod(value = "Creating a room", key = "create screening")
    public void createScreening(String movieName, String roomName, String screeningStart) {
        if (!account.isLoggedIn()) {
            return;
        }
        ScreeningDT dto = new ScreeningDT(movieName, roomName, screeningStart);
        screeningService.addScreening(dto);
    }

    @ShellMethod(value = "Deleting a room", key = "delete screening")
    public void deleteScreening(String movieName, String roomName, String screeningStart) {
        if (!account.isLoggedIn()) {
            return;
        }
        ScreeningDT dto = new ScreeningDT(movieName, roomName, screeningStart);
        screeningService.deleteScreening(dto);
    }

    @ShellMethod(value = "Listing rooms", key = "list screenings")
    public String listScreening() {
        return screeningService.listScreeningServices();
    }
}
