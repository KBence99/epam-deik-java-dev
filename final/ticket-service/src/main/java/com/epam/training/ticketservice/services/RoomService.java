package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.entities.RoomEntity;

public interface RoomService {

    public void addRoom(RoomEntity entity);

    public void updateRoom(RoomEntity entity);

    public void deleteRoom(String name);

    public String listRoom();
}
