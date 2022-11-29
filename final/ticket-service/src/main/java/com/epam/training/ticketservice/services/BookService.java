package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.dtos.ScreeningDT;

public interface BookService {

    void book(ScreeningDT screeningDT, String username, String seats);

    String getBooking(String userName);
}
