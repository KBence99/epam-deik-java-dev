package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.SignInHandler;
import com.epam.training.ticketservice.entities.RoomEntity;

import com.epam.training.ticketservice.services.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@Slf4j
@AllArgsConstructor
public class RoomCommands {

    private SignInHandler account;
    private RoomService roomService;

    @ShellMethod(value = "Creating a room", key = "create room")
    public void createRoom(String roomName, Integer chairRows, Integer chairColumns) {
        if (!account.isAdmin()) {
            return;
        }
        RoomEntity room = new RoomEntity(null,roomName, chairRows, chairColumns);
        roomService.addRoom(room);
    }

    @ShellMethod(value = "Updating a room", key = "update room")
    public void updateRoom(String roomName, Integer chairRows, Integer chairColumns) {
        if (!account.isAdmin()) {
            return;
        }
        RoomEntity room = new RoomEntity(null,roomName,chairRows,chairColumns); //TODO: Add database
        roomService.updateRoom(room);
    }

    @ShellMethod(value = "Deleting a room", key = "delete room")
    public void deleteRoom(String roomName) {
        if (!account.isAdmin()) {
            return;
        }
        roomService.deleteRoom(roomName);
    }

    @ShellMethod(value = "Listing rooms", key = "list rooms")
    public String listRoom() {
        return roomService.listRoom();
    }
}
