package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.SignInHandler;
import com.epam.training.ticketservice.dtos.ScreeningDT;
import com.epam.training.ticketservice.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class BookingCommands {

    SignInHandler signInHandler;
    BookService bookService;

    @ShellMethod(value = "Book", key = "book")
    public void book(String movieName, String roomName, String screeningStart, String seats) {
        bookService.book(new ScreeningDT(movieName, roomName, screeningStart),signInHandler.getUserName(), seats);
    }

}
