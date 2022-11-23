package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.dtos.ScreeningDT;

public interface PriceComponentService {

    public void createPrice(String name, Integer value);

    public void attachToRoom(String priceName, String roomName);

    public void attachToMovie(String priceName, String title);

    public void attachScreening(String priceName, ScreeningDT screeningDT);

    public void setBasePrice(Integer price);

    String showPrice(ScreeningDT screeningDT, String seats);
}
