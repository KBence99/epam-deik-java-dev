package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
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

    private UserHandler account;
    private RoomService roomService;

    @ShellMethod(value = "Creating a room", key = "create room")
    public void createRoom(String roomName, Integer chairRows, Integer chairColumns) {
        if (account.isAdmin()) {
            roomService.addRoom(new RoomEntity(null,roomName, chairRows, chairColumns));
        }
    }

    @ShellMethod(value = "Updating a room", key = "update room")
    public void updateRoom(String roomName, Integer chairRows, Integer chairColumns) {
        if (account.isAdmin()) {
            roomService.updateRoom(new RoomEntity(null,roomName,chairRows,chairColumns));
        }
    }

    @ShellMethod(value = "Deleting a room", key = "delete room")
    public void deleteRoom(String roomName) {
        if (account.isAdmin()) {
            roomService.deleteRoom(roomName);
        }
    }

    @ShellMethod(value = "Listing rooms", key = "list rooms")
    public String listRoom() {
        return roomService.listRoom();
    }
}
