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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService = new RoomServiceImpl(roomRepository);

    @Test
    void addRoom() {

        RoomEntity room = new RoomEntity();

        roomService.addRoom(room);

        verify(roomRepository).save(room);

    }

    @Test
    void updateRoom() {

        RoomEntity oldRoom = mock(RoomEntity.class);
        when(roomRepository.findByName("Louie")).thenReturn(Optional.of(oldRoom));

        RoomEntity room = new RoomEntity(null, "Louie", 12, 12);

        roomService.updateRoom(room);

        verify(oldRoom).setChairColumns(12);
        verify(oldRoom).setChairRows(12);
    }

    @Test
    void deleteRoom() {

        roomService.deleteRoom("Louie");

        verify(roomRepository).deleteByName("Louie");

    }

    @Test
    void listRoomEmpty() {

        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        String expected = "There are no rooms at the moment";
        String actual = roomService.listRoom();

        assertEquals(expected,actual);
    }

    @Test
    void listRoomNotEmpty() {

        List<RoomEntity> roomList = new ArrayList<>(List.of(new RoomEntity(null, "Louie", 12, 12)));

        when(roomRepository.findAll()).thenReturn(roomList);

        String expected = "Room Louie with 144 seats, 12 rows and 12 columns";
        String actual = roomService.listRoom();

        assertEquals(expected,actual);
    }
}