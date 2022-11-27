package com.epam.training.ticketservice.shellcomponent;

import com.epam.training.ticketservice.account.UserHandler;
import com.epam.training.ticketservice.entities.RoomEntity;

import com.epam.training.ticketservice.services.RoomService;
import com.epam.training.ticketservice.shellcomponent.configurations.CustomCommandClass;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@Slf4j
@AllArgsConstructor
public class RoomCommands extends CustomCommandClass {
    private RoomService roomService;

    @ShellMethod(value = "Creating a room", key = "create room")
    @ShellMethodAvailability(value = "isAdmin")
    public void createRoom(String roomName, Integer chairRows, Integer chairColumns) {
        roomService.addRoom(new RoomEntity(null,roomName, chairRows, chairColumns));
    }

    @ShellMethod(value = "Updating a room", key = "update room")
    @ShellMethodAvailability(value = "isAdmin")
    public void updateRoom(String roomName, Integer chairRows, Integer chairColumns) {
        roomService.updateRoom(new RoomEntity(null,roomName,chairRows,chairColumns));
    }

    @ShellMethod(value = "Deleting a room", key = "delete room")
    @ShellMethodAvailability(value = "isAdmin")
    public void deleteRoom(String roomName) {
        roomService.deleteRoom(roomName);
    }

    @ShellMethod(value = "Listing rooms", key = "list rooms")
    public String listRoom() {
        return roomService.listRoom();
    }
}
