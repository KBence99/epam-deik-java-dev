package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.services.BookService;
import com.epam.training.ticketservice.shellcomponent.configurations.CustomCommandClass;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class BookingCommands extends CustomCommandClass {

    UserHandler signInHandler = super.signInHandler;
    BookService bookService;

    @ShellMethod(value = "Book", key = "book")
    public void book(String movieName, String roomName, String screeningStart, String seats) {
        bookService.book(new ScreeningDT(movieName, roomName, screeningStart),signInHandler.getUserName(), seats);
    }
}
