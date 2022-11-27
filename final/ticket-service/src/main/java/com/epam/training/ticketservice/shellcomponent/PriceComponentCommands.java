package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.services.PriceComponentService;
import com.epam.training.ticketservice.shellcomponent.configurations.CustomCommandClass;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@AllArgsConstructor
public class PriceComponentCommands extends CustomCommandClass {

    PriceComponentService service;

    @ShellMethod(value = "Creates price component", key = "create price component")
    @ShellMethodAvailability(value = "isAdmin")
    public void createPriceComponent(String name, Integer value) {
        service.createPrice(name, value);
    }

    @ShellMethod(value = "Attaching price to room", key = "attach price component to room")
    @ShellMethodAvailability(value = "isAdmin")
    public void attachPriceToRoom(String priceName, String roomName) {
        service.attachToRoom(priceName, roomName);
    }

    @ShellMethod(value = "Attaching price to movie", key = "attach price component to movie")
    @ShellMethodAvailability(value = "isAdmin")
    public void attachPriceToMovie(String priceName, String movieTitle) {
        service.attachToMovie(priceName, movieTitle);
    }

    @ShellMethod(value = "Attaching price to screening", key = "attach price component to screening")
    @ShellMethodAvailability(value = "isAdmin")
    public void attachPriceToScreening(String priceName, String movieTitle, String roomName, String start) {
        service.attachScreening(priceName, new ScreeningDT(movieTitle, roomName, start));
    }

    @ShellMethod(value = "Updates base price", key = "update base price")
    @ShellMethodAvailability(value = "isAdmin")
    public void updateBasePrice(Integer price) {
        service.setBasePrice(price);
    }

    @ShellMethod(value = "Shows price", key = "show price for")
    public String showPrice(String movieTitle, String roomName, String start, String seats) {
        return service.showPrice(new ScreeningDT(movieTitle, roomName, start), seats);
    }
}