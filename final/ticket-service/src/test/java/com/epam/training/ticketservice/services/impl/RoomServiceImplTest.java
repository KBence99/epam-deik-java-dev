package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.services.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository repository;

    @InjectMocks
    private RoomService roomService = new RoomServiceImpl(repository);

    @Test
    void addRoom() {

        RoomEntity room = new RoomEntity();

        roomService.addRoom(room);

        verify(repository).save(room);

    }

    @Test
    void updateRoom() {

        RoomEntity oldRoom = mock(RoomEntity.class);
        when(repository.findByName("Louie")).thenReturn(oldRoom);

        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);

        roomService.updateRoom(room);

        verify(oldRoom).setChairColumns(12);
        verify(oldRoom).setChairRows(12);
    }

    @Test
    void deleteRoom() {

        roomService.deleteRoom("Louie");

        verify(repository).deleteByName("Louie");

    }

    @Test
    void listRoomEmpty() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        String expected = "There are no rooms at the moment";
        String actual = roomService.listRoom();

        assertEquals(expected,actual);
    }

    @Test
    void listRoomNotEmpty() {

        List<RoomEntity> roomList = new ArrayList<>(List.of(new RoomEntity(null, "Louie", 12, 12)));

        when(repository.findAll()).thenReturn(roomList);

        String expected = "Room Louie with 144 seats, 12 rows and 12 columns";
        String actual = roomService.listRoom();

        assertEquals(expected,actual);
    }
}