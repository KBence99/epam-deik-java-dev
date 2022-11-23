package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.entities.MovieEntity;
import com.epam.training.ticketservice.entities.RoomEntity;
import org.springframework.stereotype.Service;

public interface RoomService {

    public void addRoom(RoomEntity entity);

    public void updateRoom(RoomEntity entity);

    public void deleteRoom(String name);

    public String listRoom();
}
