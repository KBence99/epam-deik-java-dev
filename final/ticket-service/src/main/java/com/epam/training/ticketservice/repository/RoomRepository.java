package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    RoomEntity findByName(String name);

    void deleteByName(String name);
}
