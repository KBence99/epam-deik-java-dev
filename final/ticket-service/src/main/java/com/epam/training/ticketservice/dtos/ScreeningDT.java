package com.epam.training.ticketservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScreeningDT {
    private String movieName;
    private String roomName;
    private String screeningStart;
}
