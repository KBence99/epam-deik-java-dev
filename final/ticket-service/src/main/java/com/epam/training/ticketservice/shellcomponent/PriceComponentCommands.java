package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.services.PriceComponentService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class PriceComponentCommands {

    PriceComponentService service;

    UserHandler handler;

    @ShellMethod(value = "Creates price component", key = "create price component")
    public void createPriceComponent(String name, Integer value) {
        if (handler.isAdmin()) {
            service.createPrice(name, value);
        }
    }

    @ShellMethod(value = "Attaching price to room", key = "attach price component to room")
    public void attachPriceToRoom(String priceName, String roomName) {
        if (handler.isAdmin()) {
            service.attachToRoom(priceName, roomName);
        }
    }

    @ShellMethod(value = "Attaching price to movie", key = "attach price component to movie")
    public void attachPriceToMovie(String priceName, String movieTitle) {
        if (handler.isAdmin()) {
            service.attachToMovie(priceName, movieTitle);
        }
    }

    @ShellMethod(value = "Attaching price to screening", key = "attach price component to screening")
    public void attachPriceToScreening(String priceName, String movieTitle, String roomName, String start) {
        if (handler.isAdmin()) {
            service.attachScreening(priceName, new ScreeningDT(movieTitle, roomName, start));
        }
    }

    @ShellMethod(value = "Updates base price", key = "update base price")
    public void updateBasePrice(Integer price) {
        if (handler.isAdmin()) {
            service.setBasePrice(price);
        }
    }

    @ShellMethod(value = "Shows price", key = "show price for")
    public String showPrice(String movieTitle, String roomName, String start, String seats) {
        return service.showPrice(new ScreeningDT(movieTitle, roomName, start), seats);
    }
}