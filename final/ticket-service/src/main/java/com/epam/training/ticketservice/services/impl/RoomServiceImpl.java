package com.epam.training.ticketservice.services.impl;

import com.epam.training.ticketservice.entities.RoomEntity;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.services.RoomService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;

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
        repository.deleteByName(name);
    }

    @Override
    public String listRoom() {

        List<RoomEntity> rooms = repository.findAll();

        if (rooms.size() == 0) {
            return "There are no rooms at the moment";
        }

        StringJoiner joiner = new StringJoiner("\n");

        for (RoomEntity room:rooms) {
            joiner.add(String.format("Room %s with %d seats, %d rows and %d columns",
                    room.getName(),room.getChairRows() * room.getChairColumns(),
                    room.getChairRows(), room.getChairColumns()));
        }

        return joiner.toString();
    }
}
