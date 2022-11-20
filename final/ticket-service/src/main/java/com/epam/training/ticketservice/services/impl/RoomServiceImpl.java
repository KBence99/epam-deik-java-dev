package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private RoomRepository repository;

    @Override
    public void addRoom(RoomEntity entity) {
        repository.save(entity);
    }

    @Override
    public void updateRoom(RoomEntity entity) {
        RoomEntity room = repository.findByName(entity.getName());
        room.setChairRows(entity.getChairRows());
        room.setChairColumns(entity.getChairColumns());
    }

    @Override
    public void deleteRoom(String name) {
        RoomEntity entity = repository.findByName(name);
        repository.delete(entity);
    }

    @Override
    public String listRoom() {

        List<RoomEntity> rooms = repository.findAll();

        if (rooms.size() == 0) {
            return "There are no rooms at the moment";
        }

        StringBuilder list = new StringBuilder();

        for (RoomEntity room:rooms) {
            list.append(String.format("Room %s with %d seats, %d rows and %d columns\n",
                    room.getName(),room.getChairRows() * room.getChairColumns(),
                    room.getChairRows(), room.getChairColumns()));
        }

        return list.toString().trim();
    }
}
