package com.epam.training.ticketservice.dtos;

import lombok.Data;

@Data
public class Seat {

    int row;
    int column;

    public Seat(String seat) {
        String[] coordinate = seat.split(",");
        row = Integer.parseInt(coordinate[0]);
        column = Integer.parseInt(coordinate[1]);
    }

    @Override
    public String toString() {
        return row + "," + column;
    }
}
