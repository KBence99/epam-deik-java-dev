package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.dtos.ScreeningDT;

public interface ScreeningService {

    public void addScreening(ScreeningDT screening);

    public void deleteScreening(ScreeningDT screening);

    public String listScreenings();
}
